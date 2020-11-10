package com.demo.infrastructure.parsing

import com.demo.api.API.{BatchClosureRequest, BatchClosureResponse}
import com.demo.core.HealthCheckHandler.HealthCheckResponse
import spray.json.DefaultJsonProtocol

object JsonFormats {
  import DefaultJsonProtocol._

  implicit val healthcheckJsonFormat = jsonFormat1(HealthCheckResponse)

  implicit val batchClosureResponseFormat = jsonFormat1(BatchClosureResponse)
}
