package ru.otus.spring.hw3.game;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Locale;

@Configuration
public class AppConfig {

    @Bean
    public Locale locale(ApplicationProperties applicationProperties) {
        return new Locale(applicationProperties.getLanguage());
    }

    @Bean
    Resource questionsResource(ApplicationProperties applicationProperties) {
        return new ClassPathResource(applicationProperties.getQuestionsCSVFile());
    }
}
