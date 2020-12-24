package nubank.authorizer.account.core.exceptions

class InvalidLimitException : RuntimeException() {

    override val message = "Limit is not valid. It must be positive"
}
