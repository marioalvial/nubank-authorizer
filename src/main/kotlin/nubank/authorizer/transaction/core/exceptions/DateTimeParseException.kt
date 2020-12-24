package nubank.authorizer.transaction.core.exceptions

class DateTimeParseException : RuntimeException() {

    override val message = "Time could not be parsed to ZonedDateTime"
}
