package ru.otus.spring.hw3.game;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
public class ApplicationProperties {
    /**
     * application locale id
     */
    private String language;

    /**
     * location of csv file with questions
     */
    private String questionsCSVFile;

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setQuestionsCSVFile(String questionsCSVFile) {
        this.questionsCSVFile = questionsCSVFile;
    }

    public String getLanguage() {
        return language;
    }

    public String getQuestionsCSVFile() {
        return questionsCSVFile;
    }
}
