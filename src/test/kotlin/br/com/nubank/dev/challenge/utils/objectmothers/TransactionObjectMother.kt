package br.com.nubank.dev.challenge.utils.objectmothers

import nubank.authorizer.transaction.application.dtos.TransactionDTO
import nubank.authorizer.transaction.core.domain.Transaction

object TransactionObjectMother {

    fun new(transactionDTO: TransactionDTO = TransactionDTOObjectMother.new()) = Transaction.create(transactionDTO)
}
