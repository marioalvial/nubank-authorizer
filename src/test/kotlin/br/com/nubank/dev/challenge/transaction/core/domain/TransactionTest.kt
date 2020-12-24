package br.com.nubank.dev.challenge.transaction.core.domain

import br.com.nubank.dev.challenge.utils.objectmothers.TransactionDTOObjectMother
import br.com.nubank.dev.challenge.utils.objectmothers.TransactionObjectMother
import nubank.authorizer.transaction.core.domain.Transaction
import nubank.authorizer.transaction.core.exceptions.DateTimeParseException
import nubank.authorizer.transaction.core.exceptions.InvalidAmountException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class TransactionTest {

    @Nested
    @DisplayName("When creating a Transaction")
    inner class CreateTransaction {

        @Test
        fun `given a valid transactionRequest should create a transaction with success`() {
            val transactionDTO = TransactionDTOObjectMother.new()
            val expectedTransaction = TransactionObjectMother.new()

            val transaction = Transaction.create(transactionDTO)

            assertThat(transaction).isEqualTo(expectedTransaction)
        }

        @Test
        fun `given an amount less than 0 should throw InvalidAmountException`() {
            val transactionDTO = TransactionDTOObjectMother.new(amount = BigDecimal(-1))

            assertThatExceptionOfType(InvalidAmountException::class.java)
                .isThrownBy { Transaction.create(transactionDTO) }
                .withMessage("Transaction amount is not valid")
        }

        @Test
        fun `given an time that cannot be parsed to ZonedDateTime should throw DateTimeParseException`() {
            val transactionDTO = TransactionDTOObjectMother.new(time = "2019-02-13T11:02:01.000")

            assertThatExceptionOfType(DateTimeParseException::class.java)
                .isThrownBy { Transaction.create(transactionDTO) }
                .withMessage("Time could not be parsed to ZonedDateTime")
        }
    }
}
