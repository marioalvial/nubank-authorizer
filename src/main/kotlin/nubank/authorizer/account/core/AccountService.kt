package nubank.authorizer.account.core

import nubank.authorizer.account.application.dtos.AccountDTO
import nubank.authorizer.account.core.domain.Account
import nubank.authorizer.account.core.domain.AccountState
import nubank.authorizer.account.core.domain.violations.AccountAlreadyInitialized
import nubank.authorizer.account.core.ports.AccountRepository
import nubank.authorizer.commons.Violation
import java.math.BigDecimal

class AccountService(
    private val accountRepository: AccountRepository
) {

    fun create(accountDTO: AccountDTO): Pair<List<Violation>, Account> {
        val account = accountRepository.get()

        return if (account != null) {
            listOf(AccountAlreadyInitialized()) to account
        } else {
            Account.create(accountDTO)
                .also { accountRepository.save(it) }
                .let { emptyList<Violation>() to it }
        }
    }

    fun get() = accountRepository.get()

    fun getAccountState(transactionSum: BigDecimal) = accountRepository
        .get()
        ?.let { AccountState.create(it, transactionSum) }
}
