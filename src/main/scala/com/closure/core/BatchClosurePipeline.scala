package com.closure.core

import java.nio.file.{Path, Paths}
import java.time.LocalDate

import akka.stream.alpakka.slick.scaladsl.Slick
import akka.stream.scaladsl.{FileIO, Keep}
import akka.util.ByteString
import com.closure.domain.Transaction
import com.closure.infrastructure.database.DbSupport
import com.closure.infrastructure.repositories.TransactionTable.table
import slick.jdbc.MySQLProfile.api._

object BatchClosurePipeline extends DbSupport {

  def batchClosurePipeline(idSite: Long, mdp: Int) = {
    val closureFile: Path = Paths.get(s"output/batch-closure-${LocalDate.now()}.txt")

    Slick
      .source(table.result)
      .map(tx => formatter(tx))
      .map(tx => ByteString(tx + "\n"))
      .toMat(FileIO.toPath(closureFile))(Keep.right)
  }

  private def formatter(tx: Transaction): String =
    s"TFH----${tx.copy(cardNumber = "xxxx-xxxx-xxxx-xxxx")}---TFS"
}
