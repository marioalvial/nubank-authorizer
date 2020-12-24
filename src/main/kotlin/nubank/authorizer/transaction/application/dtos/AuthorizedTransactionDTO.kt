package nubank.authorizer.transaction.application.dtos

import nubank.authorizer.account.core.domain.AccountState
import nubank.authorizer.commons.Violation

class AuthorizedTransactionDTO(
    val account: AccountState?,
    val violations: List<String>
) {

    companion object {
        fun create(account: AccountState?, violations: List<Violation>) = AuthorizedTransactionDTO(
            account = account,
            violations = violations.map { it.message() }
        )
    }
}
