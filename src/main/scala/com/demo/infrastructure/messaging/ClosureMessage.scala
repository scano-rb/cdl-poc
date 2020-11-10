package com.demo.infrastructure.messaging

import java.time.LocalDateTime

case class ClosureMessage(content: String, result: String = "ok", date: LocalDateTime = LocalDateTime.now)
