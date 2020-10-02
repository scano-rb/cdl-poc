package com.closure.infrastructure.generator

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Sink, Source}
import com.typesafe.config.ConfigFactory

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object DataGenerator extends App {

  implicit val system = ActorSystem("DataGenerator")

  val config = ConfigFactory.load()
  val numberOfRecords = config.getInt("generator.number-of-records")

  val result = Source(1 to numberOfRecords).runWith(Sink.foreach(println))

  Await.ready(result, Duration.Inf)
  system.log.info("Generated random data")

  system.terminate()
}
