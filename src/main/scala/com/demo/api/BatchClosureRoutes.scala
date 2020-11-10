package com.demo.api

import akka.actor.typed.scaladsl.AskPattern._
import akka.actor.typed.{ActorRef, ActorSystem}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives.{complete, pathPrefix, post}
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import com.demo.api.API.BatchClosureResponse
import com.demo.core.BatchClosureHandler
import com.demo.core.BatchClosureHandler.BatchClosure
import com.demo.infrastructure.parsing.JsonFormats._

import scala.concurrent.Future

class BatchClosureRoutes(
    batchClosureHandler: ActorRef[BatchClosureHandler.Command]
  )(implicit val system: ActorSystem[_]) {

  implicit private val timeout = Timeout.create(system.settings.config.getDuration("api.routes.ask-timeout"))

  private def batchClosure(): Future[BatchClosureResponse] =
    batchClosureHandler.ask(BatchClosure(_))

  val routes: Route =
    pathPrefix("batchclosure") {
      post {
        complete(batchClosure())
      }
    }
}

object API {
  final case class BatchClosureRequest()
  final case class BatchClosureResponse(message: String)
}
