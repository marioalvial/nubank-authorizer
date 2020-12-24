package nubank.authorizer.account.core.domain

import nubank.authorizer.account.application.dtos.AccountDTO
import nubank.authorizer.account.core.exceptions.InvalidLimitException
import java.math.BigDecimal

data class Account(
    val activeCard: Boolean,
    val limit: BigDecimal
) {

    companion object {
        fun create(accountDTO: AccountDTO): Account {
            require(accountDTO.availableLimit > BigDecimal.ZERO) { throw InvalidLimitException() }

            return Account(
                activeCard = accountDTO.activeCard,
                limit = accountDTO.availableLimit
            )
        }
    }
}
