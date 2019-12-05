package ru.otus.spring.game;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Locale;

@PropertySource("classpath:application.properties")
@Configuration
public class AppConfig {

    @Bean
    public MessageSource questions(
            @Value("${questionsBundleName}") String questionsBundleName
    ) {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setDefaultEncoding("UTF-8");
        ms.setBasename(questionsBundleName);
        return ms;
    }

    @Bean
    public Locale locale(@Value("${language}") String language) {
        return new Locale(language);
    }

    @Bean
    Resource questionsResource(@Value("${questionsCSVFile}") String csvFile) {
        return new ClassPathResource(csvFile);
    }
}
