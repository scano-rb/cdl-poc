package com.demo.core

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

object HealthCheckHandler {

  // actor protocol
  sealed trait Command
  final case class GetStatus(replyTo: ActorRef[HealthCheckResponse]) extends Command

  final case class HealthCheckResponse(message: String)

  def apply(): Behavior[Command] = handler()

  // actor behavior
  private def handler(): Behavior[Command] =
    Behaviors.receiveMessage {
      case GetStatus(replyTo) =>
        replyTo ! HealthCheckResponse("OK")
        Behaviors.same
    }
}
