package nubank.authorizer.transaction.core.domain

import nubank.authorizer.transaction.application.dtos.TransactionDTO
import nubank.authorizer.transaction.core.exceptions.DateTimeParseException
import nubank.authorizer.transaction.core.exceptions.InvalidAmountException
import java.math.BigDecimal
import java.time.ZonedDateTime

data class Transaction(
    val merchant: String,
    val amount: BigDecimal,
    val time: ZonedDateTime
) {

    companion object {
        fun create(transactionDTO: TransactionDTO): Transaction {
            require(transactionDTO.amount > BigDecimal.ZERO) { throw InvalidAmountException() }

            val time = runCatching { ZonedDateTime.parse(transactionDTO.time) }
                .getOrElse { throw DateTimeParseException() }

            return Transaction(
                merchant = transactionDTO.merchant,
                amount = transactionDTO.amount,
                time = time
            )
        }
    }
}
