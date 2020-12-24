package nubank.authorizer.transaction.application.dtos

import java.math.BigDecimal

class TransactionDTO(
    val merchant: String,
    val amount: BigDecimal,
    val time: String
)
