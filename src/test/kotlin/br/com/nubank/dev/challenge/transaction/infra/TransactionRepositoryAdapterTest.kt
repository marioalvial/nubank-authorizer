package br.com.nubank.dev.challenge.transaction.infra

import br.com.nubank.dev.challenge.utils.objectmothers.TransactionObjectMother
import nubank.authorizer.transaction.infra.adapters.TransactionRepositoryAdapter
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TransactionRepositoryAdapterTest {

    @Nested
    @DisplayName("When saving a Transaction")
    inner class SaveTransaction {

        private val transactionRepositoryAdapter = TransactionRepositoryAdapter()

        @Test
        fun `given valid Transaction should save it`() {
            val transaction = TransactionObjectMother.new()

            assertThatCode { transactionRepositoryAdapter.save(transaction) }.doesNotThrowAnyException()
        }
    }

    @Nested
    @DisplayName("When finding Transactions")
    inner class FindTransactions {

        private val transactionRepositoryAdapter = TransactionRepositoryAdapter()

        @Test
        fun `given transactions should return it`() {
            transactionRepositoryAdapter.save(TransactionObjectMother.new())

            assertThat(transactionRepositoryAdapter.findAll()).hasSize(1)
        }
    }
}
