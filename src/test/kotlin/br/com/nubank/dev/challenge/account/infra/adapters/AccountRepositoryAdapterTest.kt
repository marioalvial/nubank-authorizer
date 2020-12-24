package br.com.nubank.dev.challenge.account.infra.adapters

import br.com.nubank.dev.challenge.utils.objectmothers.AccountObjectMother
import nubank.authorizer.account.infra.adapters.AccountRepositoryAdapter
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AccountRepositoryAdapterTest {

    private val accountRepositoryAdapter = AccountRepositoryAdapter()

    @Nested
    @DisplayName("When saving an Account")
    inner class SaveAccount {

        @Test
        fun `given valid account should save it`() {
            val account = AccountObjectMother.new()

            assertThatCode { accountRepositoryAdapter.save(account) }.doesNotThrowAnyException()
        }
    }

    @Nested
    @DisplayName("When getting an Account")
    inner class GetAccount {

        @Test
        fun `given existent account should return it`() {
            accountRepositoryAdapter.save(AccountObjectMother.new())

            assertThat(accountRepositoryAdapter.get()).isNotNull
        }

        @Test
        fun `given not existent account should return null`() {
            val newAccountRepositoryAdapter = AccountRepositoryAdapter()

            assertThat(newAccountRepositoryAdapter.get()).isNull()
        }
    }
}
