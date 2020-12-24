package br.com.nubank.dev.challenge.transaction.core.domain.violations

import nubank.authorizer.transaction.core.domain.violations.CardBlocked
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CardBlockedTest {

    @Nested
    @DisplayName("When getting CardBlocked violation message")
    inner class GetCardBlockViolationMessage {

        @Test
        fun `should return card-blocked`() {
            assertThat(CardBlocked().message()).isEqualTo("card-blocked")
        }
    }
}
