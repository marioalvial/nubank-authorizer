package br.com.nubank.dev.challenge.account.core.domain.violations

import nubank.authorizer.account.core.domain.violations.AccountNotInitialized
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AccountNotInitializedTest {

    @Nested
    @DisplayName("When getting AccountNotInitialized violation message")
    inner class AccountNotInitializedViolationMessage {

        @Test
        fun `should return account-not-initialized`() {
            assertThat(AccountNotInitialized().message()).isEqualTo("account-not-initialized")
        }
    }
}
