package br.com.nubank.dev.challenge.utils.objectmothers

import nubank.authorizer.account.application.dtos.AccountDTO
import java.math.BigDecimal

object AccountDTOObjectMother {

    fun new(activeCard: Boolean = true, availableLimit: BigDecimal = BigDecimal.TEN) = AccountDTO(
        activeCard = activeCard,
        availableLimit = availableLimit
    )
}
