package com.demo.api

import akka.actor.typed.scaladsl.AskPattern._
import akka.actor.typed.{ActorRef, ActorSystem}
import akka.http.scaladsl.server.Directives.{complete, get, pathPrefix}
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import com.demo.core.HealthCheckHandler
import com.demo.core.HealthCheckHandler.{GetStatus, HealthCheckResponse}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import com.demo.infrastructure.parsing.JsonFormats._

import scala.concurrent.Future

class HealthCheckRoutes(healthcheckHandler: ActorRef[HealthCheckHandler.Command])(implicit val system: ActorSystem[_]) {

  implicit private val timeout = Timeout.create(system.settings.config.getDuration("api.routes.ask-timeout"))

  def healthcheck(): Future[HealthCheckResponse] =
    healthcheckHandler.ask(GetStatus)

  val routes: Route =
    pathPrefix("healthcheck") {
      get {
        complete(healthcheck())
      }
    }
}
