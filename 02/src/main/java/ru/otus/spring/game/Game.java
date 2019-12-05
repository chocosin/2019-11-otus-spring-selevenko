package ru.otus.spring.game;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.regex.Pattern;

@Service
public class Game {
    private static final Pattern NAME_PATTERN = Pattern.compile("\\w+ \\w+");
    private final GameConfig gameConfig;
    private final Player player;

    private final Locale locale;
    private final MessageSource messageSource;

    public Game(
            GameConfig gameConfig,
            Player player,
            Locale locale,
            @Qualifier("questions") MessageSource messageSource
    ) {
        this.gameConfig = gameConfig;
        this.player = player;
        this.locale = locale;
        this.messageSource = messageSource;
    }

    public void play() {
        String name = askForName();

        int correctAnswers = 0;
        int numberOfQuestions = gameConfig.getQuestionCount();
        if (numberOfQuestions == 0) {
            player.showResult(getMessage("noQuestions"));
            return;
        }

        for (int questionInd = 0; questionInd < numberOfQuestions; questionInd++) {
            Question question = gameConfig.getQuestion(questionInd);
            String questionText = getMessage(question.getQuestion());
            String questionNumberText = getQuestionNumberText(questionInd);
            String playerAnswer = player.askQuestion(questionNumberText + ". " + questionText);
            if (question.isAnswerCorrect(playerAnswer)) {
                correctAnswers++;
            }
        }

        String result = messageSource.getMessage("gameResult", new Object[]{correctAnswers, name}, locale);
        player.showResult(result);
    }

    private String getQuestionNumberText(int questionInd) {
        return messageSource.getMessage("questionNumber", new Object[]{questionInd + 1}, locale);
    }

    private String getMessage(String question2) {
        return messageSource.getMessage(question2, null, locale);
    }

    private String askForName() {
        while (true) {
            String name = player.askQuestion(getMessage("enterYourName")).trim();
            if (NAME_PATTERN.matcher(name).matches()) {
                return name;
            }
            player.showHint(getMessage("nameHint"));
        }
    }
}
