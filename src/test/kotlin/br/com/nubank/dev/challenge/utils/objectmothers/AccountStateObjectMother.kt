package br.com.nubank.dev.challenge.utils.objectmothers

import nubank.authorizer.account.core.domain.Account
import nubank.authorizer.account.core.domain.AccountState
import java.math.BigDecimal

object AccountStateObjectMother {

    fun new(account: Account = AccountObjectMother.new(), transactionSum: BigDecimal = BigDecimal.ONE) =
        AccountState.create(account, transactionSum)
}
