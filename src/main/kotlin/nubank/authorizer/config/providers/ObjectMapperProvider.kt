package nubank.authorizer.config.providers

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object ObjectMapperProvider {

    fun provide() = jacksonObjectMapper().apply {
        propertyNamingStrategy = PropertyNamingStrategy.KEBAB_CASE

        enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }
}
