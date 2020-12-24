package nubank.authorizer.account.core.domain.violations

import nubank.authorizer.commons.Violation

class AccountNotInitialized : Violation {

    override fun message() = "account-not-initialized"
}
