package com.closure.domain

case class Transaction(
    id: Option[Int],
    terminalNumber: String,
    idSite: String,
    paymentMethod: Int,
    mcc: Int,
    amount: BigDecimal,
    cardNumber: String)
