package nubank.authorizer.transaction.core.ports

import nubank.authorizer.transaction.core.domain.Transaction

interface TransactionRepository {

    fun save(transaction: Transaction)

    fun findAll(): List<Transaction>
}
