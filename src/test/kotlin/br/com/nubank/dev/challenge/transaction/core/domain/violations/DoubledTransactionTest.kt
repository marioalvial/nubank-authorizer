package br.com.nubank.dev.challenge.transaction.core.domain.violations

import nubank.authorizer.transaction.core.domain.violations.DoubledTransaction
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DoubledTransactionTest {

    @Nested
    @DisplayName("When getting DobuledTransaction violation message")
    inner class GetDoubledTransactionViolationMessage {

        @Test
        fun `should return doubled-transaction`() {
            assertThat(DoubledTransaction().message()).isEqualTo("doubled-transaction")
        }
    }
}
