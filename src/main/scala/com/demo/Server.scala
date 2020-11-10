package com.demo

import akka.Done
import akka.actor.CoordinatedShutdown
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.adapter._
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.demo.core.{BatchClosureHandler, HealthCheckHandler, Notificator}
import com.demo.api.{BatchClosureRoutes, HealthCheckRoutes}
import com.demo.infrastructure.database.DbSupport
import com.demo.infrastructure.messaging.KafkaClient
import com.typesafe.config.ConfigFactory

import scala.concurrent.Future
import scala.util.{Failure, Success}

object Server extends DbSupport {

  private def startHttpServer(routes: Route, system: ActorSystem[_]): Unit = {

    implicit val classicSystem: akka.actor.ActorSystem = system.toClassic
    import system.executionContext

    val futureBinding = Http().bindAndHandle(routes, "localhost", 8074)
    futureBinding.onComplete {
      case Success(binding) =>
        val address = binding.localAddress
        system.log.info("Server online at http://{}:{}/", address.getHostString, address.getPort)
      case Failure(ex) =>
        system.log.error("Failed to bind HTTP endpoint, terminating system", ex)
        system.terminate()
    }
  }

  def main(args: Array[String]): Unit = {

    val rootBehavior = Behaviors.setup[Nothing] { context =>
      // config
      val config = ConfigFactory.load()

      // kafka
      val kafkaClient =
        KafkaClient(config.getString("kafka.bootstrap-servers"), config.getString("kafka.producer-topic"))

      // Actors
      val healthCheckHandlerActor =
        context.spawn(HealthCheckHandler(), "HealthCheckHandlerActor")
      context.watch(healthCheckHandlerActor)

      val notificator =
        context.spawn(Notificator(kafkaClient), "NotificatorActor")
      context.watch(notificator)

      val batchClosureHandlerActor =
        context.spawn(BatchClosureHandler(notificator), "BatchClosureHandlerActor")
      context.watch(batchClosureHandlerActor)

      // Routes
      val healthCheckRoutes  = new HealthCheckRoutes(healthCheckHandlerActor)(context.system)
      val batchClosureRoutes = new BatchClosureRoutes(batchClosureHandlerActor)(context.system)

      val routes = healthCheckRoutes.routes ~ batchClosureRoutes.routes

      startHttpServer(routes, context.system)

      Behaviors.empty
    }
    val system = ActorSystem[Nothing](rootBehavior, "BatchCloseServer")

    implicit val executionContext = system.executionContext

    CoordinatedShutdown(system)
      .addTask(CoordinatedShutdown.PhaseBeforeActorSystemTerminate, "closeConections") { () =>
        Future {
          session.close()
          Done
        }
      }
  }
}
