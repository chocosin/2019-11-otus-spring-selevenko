package ru.otus.spring.hw4

import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import java.util.*

@Component
class LocalizedMessageSource(
        private val messageSource: MessageSource,
        applicationProperties: ApplicationProperties
) {
    private val locale = Locale(applicationProperties.language)

    fun getMessage(code: String, vararg args: Any?) = messageSource.getMessage(code, args, locale)
}
