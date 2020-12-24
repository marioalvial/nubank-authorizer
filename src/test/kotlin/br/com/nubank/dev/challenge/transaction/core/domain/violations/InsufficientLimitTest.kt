package br.com.nubank.dev.challenge.transaction.core.domain.violations

import nubank.authorizer.transaction.core.domain.violations.InsufficientLimit
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class InsufficientLimitTest {

    @Nested
    @DisplayName("When getting InsufficientLimit violation message")
    inner class GetInsufficientLimitViolationMessage {

        @Test
        fun `should return not-enough-balance`() {
            assertThat(InsufficientLimit().message()).isEqualTo("not-enough-balance")
        }
    }
}
