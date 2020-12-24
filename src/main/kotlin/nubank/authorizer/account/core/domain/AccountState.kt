package nubank.authorizer.account.core.domain

import nubank.authorizer.account.core.exceptions.InvalidAccountStateException
import java.math.BigDecimal

data class AccountState(
    val activeCard: Boolean,
    val availableLimit: BigDecimal
) {

    fun hasAvailableLimit(amount: BigDecimal) = availableLimit >= amount

    companion object {
        fun create(account: Account, transactionSum: BigDecimal): AccountState {
            val limit = account.limit - transactionSum

            require(limit >= BigDecimal.ZERO) { throw InvalidAccountStateException() }

            return AccountState(
                activeCard = account.activeCard,
                availableLimit = limit
            )
        }
    }
}
