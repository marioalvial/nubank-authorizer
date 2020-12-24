package nubank.authorizer.account.core.exceptions

class InvalidAccountStateException : RuntimeException() {

    override val message = "Invalid account state. Transaction sum cannot exceed account limit"
}
