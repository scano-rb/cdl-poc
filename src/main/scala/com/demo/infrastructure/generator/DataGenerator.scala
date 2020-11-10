package com.demo.infrastructure.generator

import akka.actor.ActorSystem
import akka.stream.alpakka.slick.scaladsl.Slick
import akka.stream.scaladsl.Source
import com.demo.domain.Transaction
import com.demo.infrastructure.database.DbSupport
import com.demo.infrastructure.repositories.TransactionTable.table
import com.typesafe.config.ConfigFactory
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Transaction generator stream.
  *
  * TODO add faker to generate elements with more realistic data
  *  (now it creates the same element multiple times)
  */
object DataGenerator extends App with DbSupport {

  implicit val system = ActorSystem("DataGenerator")

  val config          = ConfigFactory.load()
  val numberOfRecords = config.getInt("generator.number-of-records")

  val result = Source(1 to numberOfRecords)
    .map(
      _ =>
        Transaction(id = None,
                    terminalNumber = "1",
                    paymentMethod = 65,
                    amount = 10.00,
                    cardNumber = "3500-1234-1234-1243"))
    .runWith(Slick.sink(tx => table += tx))

  Await.ready(result, Duration.Inf)
  system.log.info("Data generated")

  system.terminate()
}
