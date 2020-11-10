package com.demo.domain

case class Transaction(
    id: Option[Int],
    terminalNumber: String,
    paymentMethod: Int,
    amount: BigDecimal,
    cardNumber: String)
