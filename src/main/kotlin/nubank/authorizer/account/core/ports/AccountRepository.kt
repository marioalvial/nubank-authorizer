package nubank.authorizer.account.core.ports

import nubank.authorizer.account.core.domain.Account

interface AccountRepository {
    fun save(account: Account)

    fun get(): Account?
}
