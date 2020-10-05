package com.closure.infrastructure.generator

import akka.actor.ActorSystem
import akka.stream.alpakka.slick.scaladsl.Slick
import akka.stream.scaladsl.Source
import com.closure.domain.Transaction
import com.closure.infrastructure.database.DbSupport
import com.closure.infrastructure.repositories.TransactionTable.table
import com.typesafe.config.ConfigFactory
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object DataGenerator extends App with DbSupport {

  implicit val system = ActorSystem("DataGenerator")

  val config = ConfigFactory.load()
  val numberOfRecords = config.getInt("generator.number-of-records")

  val result = Source(1 to numberOfRecords)
    .map(_ => Transaction(
      id = None,
      terminalNumber = "1",
      idSite = "00001000",
      paymentMethod = 65,
      mcc = 5399,
      amount = 10.00,
      cardNumber= "3500-1234-1234-1243"))
    .runWith(Slick.sink(tx => table += tx ))

  Await.ready(result, Duration.Inf)
  system.log.info("Data generated")

  system.terminate()
}
