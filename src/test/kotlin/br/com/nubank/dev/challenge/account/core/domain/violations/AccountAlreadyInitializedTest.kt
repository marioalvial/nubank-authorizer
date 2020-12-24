package br.com.nubank.dev.challenge.account.core.domain.violations

import nubank.authorizer.account.core.domain.violations.AccountAlreadyInitialized
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AccountAlreadyInitializedTest {

    @Nested
    @DisplayName("When getting AccountAlreadyInitialized violation message")
    inner class AccountAlreadyInitializedViolationMessage {

        @Test
        fun `should return account-already-initialized`() {
            assertThat(AccountAlreadyInitialized().message()).isEqualTo("account-already-initialized")
        }
    }
}
