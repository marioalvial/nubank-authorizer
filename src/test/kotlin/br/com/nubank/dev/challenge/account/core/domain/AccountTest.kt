package br.com.nubank.dev.challenge.account.core.domain

import br.com.nubank.dev.challenge.utils.objectmothers.AccountDTOObjectMother
import nubank.authorizer.account.core.domain.Account
import nubank.authorizer.account.core.exceptions.InvalidLimitException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class AccountTest {

    @Nested
    @DisplayName("When creating an Account")
    inner class AccountCreation {

        @Test
        fun `given a valid AccountDTO should create an Account with success`() {
            val accountDTO = AccountDTOObjectMother.new()

            val account = Account.create(accountDTO)

            assertThat(account.activeCard).isTrue
            assertThat(account.limit).isEqualTo(BigDecimal.TEN)
        }

        @Test
        fun `given a limit less than 0 should throw InvalidLimitException`() {
            val limit = BigDecimal.valueOf(-1)
            val accountDTO = AccountDTOObjectMother.new(true, limit)

            assertThatExceptionOfType(InvalidLimitException::class.java)
                .isThrownBy { Account.create(accountDTO) }
                .withMessage("Limit is not valid. It must be positive")
        }
    }
}
