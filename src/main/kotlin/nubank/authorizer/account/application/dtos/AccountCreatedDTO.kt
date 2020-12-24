package nubank.authorizer.account.application.dtos

import nubank.authorizer.account.core.domain.Account
import nubank.authorizer.commons.Violation

data class AccountCreatedDTO(
    val account: Account,
    val violations: List<String>
) {
    companion object {
        fun create(account: Account, violations: List<Violation>) = AccountCreatedDTO(
            account = account,
            violations = violations.map { it.message() }
        )
    }
}
