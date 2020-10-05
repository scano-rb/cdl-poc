package com.closure.api

import akka.actor.typed.scaladsl.AskPattern._
import akka.actor.typed.{ActorRef, ActorSystem}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives.{as, complete, entity, pathPrefix, post}
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import com.closure.api.API.{BatchClosureRequest, BatchClosureResponse}
import com.closure.core.BatchClosureHandler
import com.closure.core.BatchClosureHandler.BatchClosure
import com.closure.infrastructure.parsing.JsonFormats._

import scala.concurrent.Future

class BatchClosureRoutes(
    batchClosureHandler: ActorRef[BatchClosureHandler.Command]
  )(implicit val system: ActorSystem[_]) {

  implicit private val timeout = Timeout.create(system.settings.config.getDuration("closure-poc.routes.ask-timeout"))

  private def batchClosure(idSite: Long, paymentMethod: Int): Future[BatchClosureResponse] =
    batchClosureHandler.ask(BatchClosure(idSite, paymentMethod, _))

  val routes: Route =
    pathPrefix("batchclosure") {
      post {
        entity(as[BatchClosureRequest]) { req =>
            complete(batchClosure(req.idSite, req.paymentMethod))
        }
      }
    }
}

object API {
  final case class BatchClosureRequest(idSite: Long, paymentMethod: Int)
  final case class BatchClosureResponse(message: String)
}
