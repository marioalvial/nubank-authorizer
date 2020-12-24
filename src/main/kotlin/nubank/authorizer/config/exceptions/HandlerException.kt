package nubank.authorizer.config.exceptions

class HandlerException : RuntimeException() {

    override val message = "There is no handler for given message"
}
