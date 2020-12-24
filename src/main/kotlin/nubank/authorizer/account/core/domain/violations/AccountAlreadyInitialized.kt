package nubank.authorizer.account.core.domain.violations

import nubank.authorizer.commons.Violation

class AccountAlreadyInitialized : Violation {

    override fun message() = "account-already-initialized"
}
