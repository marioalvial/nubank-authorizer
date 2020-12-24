package nubank.authorizer.account.application.dtos

import java.math.BigDecimal

data class AccountDTO(
    val activeCard: Boolean,
    val availableLimit: BigDecimal
)
