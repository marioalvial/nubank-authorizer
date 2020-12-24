package nubank.authorizer.transaction.core.domain

import nubank.authorizer.account.core.domain.AccountState
import nubank.authorizer.transaction.core.domain.violations.CardBlocked
import nubank.authorizer.transaction.core.domain.violations.DoubledTransaction
import nubank.authorizer.transaction.core.domain.violations.HighFrequencyInSmallInterval
import nubank.authorizer.transaction.core.domain.violations.InsufficientLimit
import java.math.BigDecimal
import java.time.temporal.ChronoUnit

object TransactionAuthorizer {

    private const val INTERVAL_IN_MINUTES = 2L
    private const val MAX_TRANSACTION_NUMBER_IN_INTERVAL = 3
    private const val MAX_SIMILAR_TRANSACTION_IN_INTERVAL = 1

    fun authorize(
        account: AccountState,
        authorizedTransactions: List<Transaction>,
        transaction: Transaction
    ) = listOfNotNull(
        verifyCardStatus(account),
        verifyAccountLimit(account, transaction.amount),
        verifyRateLimit(authorizedTransactions, transaction),
        verifyUniqueness(authorizedTransactions, transaction)
    )

    private fun verifyCardStatus(account: AccountState) =
        if (!account.activeCard) CardBlocked() else null

    private fun verifyAccountLimit(account: AccountState, amount: BigDecimal) =
        if (!account.hasAvailableLimit(amount)) InsufficientLimit() else null

    private fun verifyRateLimit(transactions: List<Transaction>, transaction: Transaction) = transactions
        .filter { ChronoUnit.MINUTES.between(it.time, transaction.time) <= INTERVAL_IN_MINUTES }
        .let { if (it.size >= MAX_TRANSACTION_NUMBER_IN_INTERVAL) HighFrequencyInSmallInterval() else null }

    private fun verifyUniqueness(transactions: List<Transaction>, transaction: Transaction) = transactions
        .filter { ChronoUnit.MINUTES.between(it.time, transaction.time) <= INTERVAL_IN_MINUTES }
        .filter { it.merchant == transaction.merchant && it.amount == transaction.amount }
        .let { if (it.size > MAX_SIMILAR_TRANSACTION_IN_INTERVAL) DoubledTransaction() else null }
}
