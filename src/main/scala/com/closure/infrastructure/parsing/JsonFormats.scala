package com.closure.infrastructure.parsing

import com.closure.api.API.BatchClosureRequest
import com.closure.core.HealthCheckHandler.HealthCheckResponse
import spray.json.DefaultJsonProtocol

object JsonFormats {
  import DefaultJsonProtocol._

  implicit val healthcheckJsonFormat = jsonFormat1(HealthCheckResponse)

  implicit val batchClosureRequestFormat = jsonFormat2(BatchClosureRequest)
}
