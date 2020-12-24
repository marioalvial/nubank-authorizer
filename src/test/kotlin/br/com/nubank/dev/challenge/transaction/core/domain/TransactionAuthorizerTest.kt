package br.com.nubank.dev.challenge.transaction.core.domain

import br.com.nubank.dev.challenge.utils.objectmothers.AccountDTOObjectMother
import br.com.nubank.dev.challenge.utils.objectmothers.AccountObjectMother
import br.com.nubank.dev.challenge.utils.objectmothers.AccountStateObjectMother
import br.com.nubank.dev.challenge.utils.objectmothers.TransactionDTOObjectMother
import br.com.nubank.dev.challenge.utils.objectmothers.TransactionObjectMother
import nubank.authorizer.transaction.core.domain.Transaction
import nubank.authorizer.transaction.core.domain.TransactionAuthorizer
import nubank.authorizer.transaction.core.domain.violations.CardBlocked
import nubank.authorizer.transaction.core.domain.violations.DoubledTransaction
import nubank.authorizer.transaction.core.domain.violations.HighFrequencyInSmallInterval
import nubank.authorizer.transaction.core.domain.violations.InsufficientLimit
import nubank.authorizer.transaction.core.exceptions.DateTimeParseException
import nubank.authorizer.transaction.core.exceptions.InvalidAmountException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class TransactionAuthorizerTest {

    @Nested
    @DisplayName("When authorizing a Transaction")
    inner class AuthorizeTransaction {

        @Test
        fun `given account state with available limit should authorize the transaction`() {
            val accountState = AccountStateObjectMother.new()
            val authorizedTransactions = emptyList<Transaction>()
            val transaction = TransactionObjectMother.new()

            val violations = TransactionAuthorizer.authorize(accountState, authorizedTransactions, transaction)

            assertThat(violations).isEmpty()
        }

        @Test
        fun `given account state with card blocked should not authorize the transaction`() {
            val accountDTO = AccountDTOObjectMother.new(activeCard = false)
            val account = AccountObjectMother.new(accountDTO)
            val accountState = AccountStateObjectMother.new(account)
            val authorizedTransactions = emptyList<Transaction>()
            val transaction = TransactionObjectMother.new()

            val violations = TransactionAuthorizer.authorize(accountState, authorizedTransactions, transaction)

            assertThat(violations).hasSize(1)
            assertThat(violations.first()).isInstanceOf(CardBlocked::class.java)
        }

        @Test
        fun `given available limit less then transaction amount should not authorize the transaction`() {
            val accountState = AccountStateObjectMother.new()
            val authorizedTransactions = emptyList<Transaction>()
            val transactionDTO = TransactionDTOObjectMother.new(amount = BigDecimal.TEN)
            val transaction = TransactionObjectMother.new(transactionDTO = transactionDTO)

            val violations = TransactionAuthorizer.authorize(accountState, authorizedTransactions, transaction)

            assertThat(violations).hasSize(1)
            assertThat(violations.first()).isInstanceOf(InsufficientLimit::class.java)
        }

        @Test
        fun `given more than 3 transactions in a 2 minutes interval should not authorize the transaction`() {
            val accountState = AccountStateObjectMother.new()
            val authorizedTransactions = listOf(
                TransactionObjectMother.new(transactionDTO = TransactionDTOObjectMother.new(amount = BigDecimal.ONE)),
                TransactionObjectMother.new(transactionDTO = TransactionDTOObjectMother.new(amount = BigDecimal(2))),
                TransactionObjectMother.new(transactionDTO = TransactionDTOObjectMother.new(amount = BigDecimal(3)))
            )
            val transaction = TransactionObjectMother.new(TransactionDTOObjectMother.new(amount = BigDecimal(1)))

            val violations = TransactionAuthorizer.authorize(accountState, authorizedTransactions, transaction)

            assertThat(violations).hasSize(1)
            assertThat(violations.first()).isInstanceOf(HighFrequencyInSmallInterval::class.java)
        }

        @Test
        fun `given more than 1 similar transactions in a 2 minutes interval should not authorize the transaction`() {
            val accountState = AccountStateObjectMother.new()
            val authorizedTransactions = listOf(
                TransactionObjectMother.new(transactionDTO = TransactionDTOObjectMother.new()),
                TransactionObjectMother.new(transactionDTO = TransactionDTOObjectMother.new()),
            )
            val transaction = TransactionObjectMother.new()

            val violations = TransactionAuthorizer.authorize(accountState, authorizedTransactions, transaction)

            assertThat(violations).hasSize(1)
            assertThat(violations.first()).isInstanceOf(DoubledTransaction::class.java)
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
