package br.com.nubank.dev.challenge.utils.objectmothers

import nubank.authorizer.account.application.dtos.AccountDTO
import nubank.authorizer.account.core.domain.Account

object AccountObjectMother {

    fun new(accountDTO: AccountDTO = AccountDTOObjectMother.new()) = Account.create(accountDTO)
}
