package nubank.authorizer.transaction.core.domain.violations

import nubank.authorizer.commons.Violation

class HighFrequencyInSmallInterval : Violation {

    override fun message() = "high-frequency-in-small-interval"
}
