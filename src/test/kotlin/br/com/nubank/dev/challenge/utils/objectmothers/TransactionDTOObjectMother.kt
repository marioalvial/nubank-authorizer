package br.com.nubank.dev.challenge.utils.objectmothers

import nubank.authorizer.transaction.application.dtos.TransactionDTO
import java.math.BigDecimal

object TransactionDTOObjectMother {

    fun new(
        merchant: String = "Merchant",
        amount: BigDecimal = BigDecimal.ONE,
        time: String = "2019-02-13T11:02:01.000Z"
    ) = TransactionDTO(
        merchant = merchant,
        amount = amount,
        time = time
    )
}
