package com.demo.infrastructure.repositories

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import slick.jdbc.MySQLProfile.api._

object Maps {

  val toLocalDateTime = MappedColumnType.base[LocalDateTime, String](
    date => date.toString,
    dateString => LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
  )
}
