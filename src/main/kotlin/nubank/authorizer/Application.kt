package nubank.authorizer

import nubank.authorizer.account.application.AccountHandler
import nubank.authorizer.account.core.AccountService
import nubank.authorizer.account.infra.adapters.AccountRepositoryAdapter
import nubank.authorizer.config.exceptions.HandlerException
import nubank.authorizer.config.providers.ObjectMapperProvider
import nubank.authorizer.transaction.application.TransactionHandler
import nubank.authorizer.transaction.core.TransactionService
import nubank.authorizer.transaction.infra.adapters.TransactionRepositoryAdapter
import java.util.Scanner

fun main() {
    println("---------------- Application started. Please input commands ----------------")

    NubankAuthorizer.run()
}

object NubankAuthorizer {

    fun run() {
        val scanner = Scanner(System.`in`)
        val mapper = ObjectMapperProvider.provide()
        val accountService = AccountService(AccountRepositoryAdapter())
        val transactionService = TransactionService(accountService, TransactionRepositoryAdapter())

        while (scanner.hasNext()) {

            val json = scanner.nextLine()

            if (json.isNotBlank()) {
                val responseJson: String = when {
                    json.contains("account") -> AccountHandler(accountService, mapper).handle(json)
                    json.contains("transaction") -> TransactionHandler(transactionService).handle(json)
                    else -> throw HandlerException()
                }

                println(responseJson)
            }
        }
    }
}
