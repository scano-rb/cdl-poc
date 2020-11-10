package com.demo.core

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import com.demo.infrastructure.messaging.{ClosureMessage, KafkaClient}

/**
  * Notificator that sends a notification message to a specific kafka topic.
  * It is used to notify other services that batch processing has finished and that the
  * batch closure file has been created.
  */
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
