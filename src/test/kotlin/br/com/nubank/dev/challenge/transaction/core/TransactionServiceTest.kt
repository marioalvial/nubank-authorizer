package br.com.nubank.dev.challenge.transaction.core

import br.com.nubank.dev.challenge.utils.objectmothers.AccountStateObjectMother
import br.com.nubank.dev.challenge.utils.objectmothers.TransactionDTOObjectMother
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import nubank.authorizer.account.core.AccountService
import nubank.authorizer.account.core.domain.violations.AccountNotInitialized
import nubank.authorizer.transaction.core.TransactionService
import nubank.authorizer.transaction.core.ports.TransactionRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class TransactionServiceTest {

    private val accountService = mockk<AccountService>()
    private val transactionRepository = mockk<TransactionRepository>()
    private val transactionService = TransactionService(accountService, transactionRepository)

    @Nested
    @DisplayName("When authorizing a Transaction")
    inner class AuthorizeTransaction {

        @Test
        fun `given valid transactionDTO and existent account should create a transaction and return it`() {
            val transactionDTO = TransactionDTOObjectMother.new()
            val accountState = AccountStateObjectMother.new()
            val newAccountState = AccountStateObjectMother.new(transactionSum = BigDecimal(2))

            every { transactionRepository.findAll() } returns emptyList()
            every { accountService.getAccountState(BigDecimal.ZERO) } returns accountState
            every { accountService.getAccountState(transactionDTO.amount) } returns newAccountState
            every { transactionRepository.save(any()) } just Runs

            val (violations, account) = transactionService.authorize(transactionDTO)

            assertThat(account?.activeCard).isTrue
            assertThat(account?.availableLimit).isEqualTo(accountState.availableLimit - transactionDTO.amount)
            assertThat(violations).hasSize(0)
        }

        @Test
        fun `given not existent account should return violation`() {
            val transactionDTO = TransactionDTOObjectMother.new()

            every { transactionRepository.findAll() } returns emptyList()
            every { accountService.getAccountState(any()) } returns null

            val (violations, accountState) = transactionService.authorize(transactionDTO)

            assertThat(accountState).isNull()
            assertThat(violations).hasSize(1)
            assertThat(violations.first()).isInstanceOf(AccountNotInitialized::class.java)
        }
    }
}
