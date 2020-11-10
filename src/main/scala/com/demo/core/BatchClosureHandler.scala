package com.demo.core

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import com.demo.core.Notificator.SendMessage
import com.demo.infrastructure.messaging.ClosureMessage
import BatchClosurePipeline.batchClosurePipeline
import com.demo.api.API.BatchClosureResponse

import scala.util.{Failure, Success}

object BatchClosureHandler {

  // actor protocol
  sealed trait Command
  final case class BatchClosure(replyTo: ActorRef[BatchClosureResponse]) extends Command

  def apply(notificator: ActorRef[Notificator.Command]): Behavior[Command] =
    handler(notificator)

  // actor behavior
  private def handler(notificator: ActorRef[Notificator.Command]): Behavior[Command] =
    Behaviors.receive { (context, message) =>
      implicit val actorSystem = context.system
      implicit val dispatcher  = context.executionContext

      message match {
        case BatchClosure(replyTo) =>
          context.log.info("running batch process")
          val result = batchClosurePipeline().run()
          replyTo ! BatchClosureResponse("closure ok")

          result onComplete {
            case Success(_) =>
              val msg = ClosureMessage(s"Batch closure process finished OK")
              notificator ! SendMessage(msg)

            case Failure(_) =>
          }

          Behaviors.same
      }
    }
}
