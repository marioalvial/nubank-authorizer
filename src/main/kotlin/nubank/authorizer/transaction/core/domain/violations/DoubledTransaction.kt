package nubank.authorizer.transaction.core.domain.violations

import nubank.authorizer.commons.Violation

class DoubledTransaction : Violation {

    override fun message() = "doubled-transaction"
}
