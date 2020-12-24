
package nubank.authorizer.transaction.application

import com.fasterxml.jackson.module.kotlin.readValue
import nubank.authorizer.config.providers.ObjectMapperProvider
import nubank.authorizer.transaction.application.dtos.AuthorizeTransactionDTO
import nubank.authorizer.transaction.application.dtos.AuthorizedTransactionDTO
import nubank.authorizer.transaction.core.TransactionService

class TransactionHandler(
    private val transactionService: TransactionService
) {

    private val mapper = ObjectMapperProvider.provide()

    fun handle(json: String): String {
        val authorizeTransactionDTO = mapper.readValue<AuthorizeTransactionDTO>(json)

        return transactionService
            .authorize(authorizeTransactionDTO.transaction)
            .let { (violations, account) -> AuthorizedTransactionDTO.create(account, violations) }
            .let { mapper.writeValueAsString(it) }
    }
}
