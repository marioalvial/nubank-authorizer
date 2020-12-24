package br.com.nubank.dev.challenge.account.core

import br.com.nubank.dev.challenge.utils.objectmothers.AccountDTOObjectMother
import br.com.nubank.dev.challenge.utils.objectmothers.AccountObjectMother
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import nubank.authorizer.account.core.AccountService
import nubank.authorizer.account.core.ports.AccountRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class AccountServiceTest {

    private val accountRepository = mockk<AccountRepository>()
    private val accountService = AccountService(accountRepository)

    @Nested
    @DisplayName("When creating an Account")
    inner class AccountCreation {

        @Test
        fun `given valid accountRequest should create an account and return it with 0 violations`() {
            val accountDTO = AccountDTOObjectMother.new()

            every { accountRepository.get() } returns null
            every { accountRepository.save(any()) } just Runs

            val (violations, account) = accountService.create(accountDTO)

            assertThat(account.activeCard).isTrue
            assertThat(account.limit).isEqualTo(BigDecimal.TEN)
            assertThat(violations).hasSize(0)
        }

        @Test
        fun `given existent account when trying to create a new one should return violation and existent account`() {
            val accountDTO = AccountDTOObjectMother.new()

            every { accountRepository.get() } returns AccountObjectMother.new(accountDTO = accountDTO)

            val (violations, _) = accountService.create(accountDTO)

            assertThat(violations).hasSize(1)
            verify(exactly = 0) { accountRepository.save(any()) }
        }
    }

    @Nested
    @DisplayName("When getting an Account")
    inner class GetAccount {

        @Test
        fun `given existent account should return it`() {
            every { accountRepository.get() } returns AccountObjectMother.new()

            assertThat(accountService.get()).isNotNull
        }

        @Test
        fun `given no existent account should return null`() {
            every { accountRepository.get() } returns null

            assertThat(accountService.get()).isNull()
        }
    }

    @Nested
    @DisplayName("When getting an AccountState")
    inner class GetAccountState {

        @Test
        fun `given transactionSum should return the current state of the account`() {
            val account = AccountObjectMother.new()

            every { accountRepository.get() } returns account

            val accountState = accountService.getAccountState(BigDecimal.valueOf(3))

            assertThat(accountState?.activeCard).isEqualTo(true)
            assertThat(accountState?.availableLimit).isEqualTo(BigDecimal.valueOf(7))
        }

        @Test
        fun `given not existent account should return null`() {
            every { accountRepository.get() } returns null

            val accountState = accountService.getAccountState(BigDecimal.valueOf(3))

            assertThat(accountState).isNull()
        }
    }
}
