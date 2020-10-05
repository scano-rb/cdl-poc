package com.closure.core

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import com.closure.core.Notificator.SendMessage
import com.closure.infrastructure.messaging.ClosureMessage
import BatchClosurePipeline.batchClosurePipeline
import com.closure.api.API.BatchClosureResponse

import scala.util.{Failure, Success}

object BatchClosureHandler {

  // actor protocol
  sealed trait Command
  final case class BatchClosure(idSite: Long, paymentMethod: Int, replyTo: ActorRef[BatchClosureResponse])
      extends Command

  def apply(notificator: ActorRef[Notificator.Command]): Behavior[Command] =
    handler(notificator)

  // actor behavior
  private def handler(notificator: ActorRef[Notificator.Command]): Behavior[Command] =
    Behaviors.receive { (context, message) =>
      implicit val actorSystem = context.system
      implicit val dispatcher  = context.executionContext

      message match {
        case BatchClosure(idSite, mdp, replyTo) =>
          context.log.info("batch closing idSite: {} mdp: {}", idSite, mdp)
          val result = batchClosurePipeline(idSite, mdp).run()
          replyTo ! BatchClosureResponse("closure ok")

          result onComplete {
            case Success(_) =>
              val msg = ClosureMessage(s"Closure for site: $idSite and $mdp was OK")
              notificator ! SendMessage(msg)

            case Failure(_) =>
          }

          Behaviors.same
      }
    }
}
