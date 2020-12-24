package nubank.authorizer.transaction.core.domain.violations

import nubank.authorizer.commons.Violation

class CardBlocked : Violation {

    override fun message() = "card-blocked"
}
