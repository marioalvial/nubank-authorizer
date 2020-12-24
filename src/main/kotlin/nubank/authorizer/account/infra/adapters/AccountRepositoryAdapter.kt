package nubank.authorizer.account.infra.adapters

import nubank.authorizer.account.core.domain.Account
import nubank.authorizer.account.core.ports.AccountRepository

class AccountRepositoryAdapter : AccountRepository {

    lateinit var account: Account

    override fun save(account: Account) {
        this.account = account
    }

    override fun get() = if (::account.isInitialized) account else null
}
