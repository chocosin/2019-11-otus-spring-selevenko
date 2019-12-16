package ru.otus.spring.hw3;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocalizedMessageSource {
    private final Locale locale;
    private final MessageSource messageSource;

    public LocalizedMessageSource(Locale locale, MessageSource messageSource) {
        this.locale = locale;
        this.messageSource = messageSource;
    }

    public String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, locale);
    }
}
