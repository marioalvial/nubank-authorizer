package br.com.nubank.dev.challenge.transaction.core.domain.violations

import nubank.authorizer.transaction.core.domain.violations.HighFrequencyInSmallInterval
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class HighFrequencyInSmallIntervalTest {

    @Nested
    @DisplayName("When getting HighFrequencyInSmallInterval violation message")
    inner class GetHighFrequencyInSmallIntervalViolationMessage {

        @Test
        fun `should return high-frequency-in-small-interval`() {
            assertThat(HighFrequencyInSmallInterval().message()).isEqualTo("high-frequency-in-small-interval")
        }
    }
}
