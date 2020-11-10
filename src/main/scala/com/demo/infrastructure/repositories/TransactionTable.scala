package com.demo.infrastructure.repositories

import com.demo.domain.Transaction
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

class TransactionTable(tag: Tag) extends Table[Transaction](tag, "transactions") {

  def id: Rep[Int] = column[Int]("transaction_id", O.PrimaryKey, O.AutoInc)

  def terminalNumber: Rep[String] = column[String]("terminal_number")

  def paymentMethod: Rep[Int] = column[Int]("payment_media_id")

  def amount: Rep[BigDecimal] = column[BigDecimal]("amount")

  def cardNumber: Rep[String] = column[String]("card_number")

  def * = (id.?, terminalNumber, paymentMethod, amount, cardNumber).mapTo[Transaction]
}

object TransactionTable {
  val table = TableQuery[TransactionTable]
}
