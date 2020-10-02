package com.closure.core

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import com.closure.infrastructure.messaging.{ClosureMessage, KafkaClient}

object Notificator {

  // actor protocol
  sealed trait Command
  final case class SendMessage(message: ClosureMessage) extends Command

  def apply(kafkaClient: KafkaClient): Behavior[Command] =
    notificator(kafkaClient)

  // actor behavior
  private def notificator(kafkaClient: KafkaClient): Behavior[Command] =
    Behaviors.receive { (context, message) =>
      message match {
        case SendMessage(message) =>
          context.log.info("Sending message to topic")
          kafkaClient.publish(message.toString)
      }

      Behaviors.same
    }
}
