package nubank.authorizer.transaction.core

import nubank.authorizer.account.core.AccountService
import nubank.authorizer.account.core.domain.AccountState
import nubank.authorizer.account.core.domain.violations.AccountNotInitialized
import nubank.authorizer.commons.Violation
import nubank.authorizer.transaction.application.dtos.TransactionDTO
import nubank.authorizer.transaction.core.domain.Transaction
import nubank.authorizer.transaction.core.domain.TransactionAuthorizer
import nubank.authorizer.transaction.core.ports.TransactionRepository
import java.math.BigDecimal

class TransactionService(
    private val accountService: AccountService,
    private val transactionRepository: TransactionRepository
) {

    fun authorize(transactionDTO: TransactionDTO): Pair<List<Violation>, AccountState?> {
        val authorizedTransactions = transactionRepository.findAll()
        val transaction = Transaction.create(transactionDTO)
        val accountState = accountService.getAccountState(sumTransactionsAmount(authorizedTransactions))

        return accountState
            ?.let { TransactionAuthorizer.authorize(it, authorizedTransactions, transaction) }
            ?.let { violations ->
                if (violations.isEmpty()) {
                    transactionRepository.save(transaction)

                    accountService
                        .getAccountState(sumTransactionsAmount(authorizedTransactions + transaction))
                        .let { violations to it }
                } else {
                    violations to accountState
                }
            }
            ?: listOf(AccountNotInitialized()) to null
    }

    private fun sumTransactionsAmount(transactions: List<Transaction>) = transactions
        .fold(BigDecimal.ZERO) { accumulate, (_, amount) -> accumulate + amount }
}
