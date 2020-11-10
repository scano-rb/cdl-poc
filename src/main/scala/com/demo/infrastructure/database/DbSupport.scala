package com.demo.infrastructure.database

import akka.stream.alpakka.slick.scaladsl.SlickSession

trait DbSupport {
  implicit val session = SlickSession.forConfig("slick-postgres")
}
