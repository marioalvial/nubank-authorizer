package nubank.authorizer.transaction.infra.adapters

import nubank.authorizer.transaction.core.domain.Transaction
import nubank.authorizer.transaction.core.ports.TransactionRepository

class TransactionRepositoryAdapter : TransactionRepository {

    private val transactions = mutableListOf<Transaction>()

    override fun save(transaction: Transaction) {
        transactions.add(transaction)
    }

    override fun findAll() = transactions.toList()
}
