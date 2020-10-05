package com.closure.infrastructure.repositories

import com.closure.domain.Transaction
import slick.jdbc.MySQLProfile.api._
import slick.lifted.{ProvenShape, TableQuery}

class TransactionTable(tag: Tag) extends Table[Transaction](tag, "transaction") {

  def id: Rep[Int] = column[Int]("idtransaccion")

  def terminalNumber: Rep[String] = column[String]("nro_terminal")

  def idSite: Rep[String] = column[String]("idsite")

  def paymentMethod: Rep[Int] = column[Int]("idmediopago")

  def mcc: Rep[Int] = column[Int]("mcc")

  def amount: Rep[BigDecimal] = column[BigDecimal]("monto")

  def cardNumber: Rep[String] = column[String]("nrotarjeta")

  override def * : ProvenShape[Transaction] =
    (id.?, terminalNumber, idSite, paymentMethod, mcc, amount, cardNumber).<>(
      {
        case (id, terminalNumber, idSite, paymentMethod, mcc, amount, cardNumber) =>
          Transaction(
            id = id,
            terminalNumber = terminalNumber,
            idSite = idSite,
            paymentMethod = paymentMethod,
            mcc = mcc,
            amount = amount,
            cardNumber = cardNumber
          )
      }, { transaction: Transaction =>
        Some(
          (transaction.id,
           transaction.terminalNumber,
           transaction.idSite,
           transaction.paymentMethod,
           transaction.mcc,
           transaction.amount,
           transaction.cardNumber))
      }
    )
}

object TransactionTable {
  val table = TableQuery[TransactionTable]
}
