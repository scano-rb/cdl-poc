package com.demo.core

import java.nio.file.{Path, Paths}
import java.time.LocalDate

import akka.stream.alpakka.slick.scaladsl.Slick
import akka.stream.scaladsl.{FileIO, Keep}
import akka.util.ByteString
import com.demo.domain.Transaction
import com.demo.infrastructure.database.DbSupport
import com.demo.infrastructure.repositories.TransactionTable.table
import slick.jdbc.PostgresProfile.api._

object BatchClosurePipeline extends DbSupport {

  /**
    * Batch pipeline that read transactions from db, transforms into ISO format (dummy implementation)
    * and then dump its results into a file.
    *
    * @return
    */
  def batchClosurePipeline() = {
    val closureFile: Path = Paths.get(s"output/batch-closure-${LocalDate.now()}.txt")

    Slick
      .source(table.result)
      .map(tx => formatter(tx))
      .map(tx => ByteString(tx + "\n"))
      .toMat(FileIO.toPath(closureFile))(Keep.right)
  }

  /**
    * Dummy formatter which simulates writing an ISO record. It just adds a header per between the row content
    * card number.
    *
    * @param tx
    * @return
    */
  private def formatter(tx: Transaction): String =
    s"TFH----$tx----TFS"
}
