package nubank.authorizer.transaction.core.exceptions

class InvalidAmountException : RuntimeException() {

    override val message = "Transaction amount is not valid"
}
