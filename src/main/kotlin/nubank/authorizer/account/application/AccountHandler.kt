package nubank.authorizer.account.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import nubank.authorizer.account.application.dtos.AccountCreatedDTO
import nubank.authorizer.account.application.dtos.CreateAccountDTO
import nubank.authorizer.account.core.AccountService

class AccountHandler(
    private val accountService: AccountService,
    private val mapper: ObjectMapper
) {

    fun handle(json: String): String {
        val accountDTO = mapper.readValue<CreateAccountDTO>(json)

        return accountService
            .create(accountDTO.account)
            .let { (violations, account) -> AccountCreatedDTO.create(account, violations) }
            .let { mapper.writeValueAsString(it) }
    }
}
