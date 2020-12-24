package br.com.nubank.dev.challenge.account.core.domain

import br.com.nubank.dev.challenge.utils.objectmothers.AccountObjectMother
import nubank.authorizer.account.core.domain.AccountState
import nubank.authorizer.account.core.exceptions.InvalidAccountStateException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class AccountStateTest {

    @Nested
    @DisplayName("When creating an AccountState")
    inner class AccountStateCreation {

        @Test
        fun `given a valid Account should create an AccountState with success`() {
            val account = AccountObjectMother.new()

            val accountState = AccountState.create(account, BigDecimal.ONE)

            assertThat(accountState.activeCard).isTrue
            assertThat(accountState.availableLimit).isEqualTo(BigDecimal(9))
        }

        @Test
        fun `given an available limit less than 0 should throw InvalidAccountStateException`() {
            val account = AccountObjectMother.new()

            assertThatExceptionOfType(InvalidAccountStateException::class.java)
                .isThrownBy { AccountState.create(account, BigDecimal(100)) }
                .withMessage("Invalid account state. Transaction sum cannot exceed account limit")
        }
    }
    @Nested
    @DisplayName("When verifying if AccountState has sufficient available limit")
    inner class HasLimitVerification {

        @Test
        fun `given amount less than the available limit should return true`() {
            val account = AccountObjectMother.new()
            val accountState = AccountState.create(account, BigDecimal.ONE)

            assertThat(accountState.hasAvailableLimit(BigDecimal.ONE)).isTrue
        }

        @Test
        fun `given amount greater than the available limit should return false`() {
            val account = AccountObjectMother.new()
            val accountState = AccountState.create(account, BigDecimal.ONE)

            assertThat(accountState.hasAvailableLimit(BigDecimal.TEN)).isFalse
        }
    }
}
