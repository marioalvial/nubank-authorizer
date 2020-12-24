package nubank.authorizer.transaction.core.domain.violations

import nubank.authorizer.commons.Violation

class InsufficientLimit : Violation {

    override fun message() = "not-enough-balance"
}
