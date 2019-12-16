package ru.otus.spring.hw3.game;

import org.springframework.stereotype.Service;
import ru.otus.spring.hw3.LocalizedMessageSource;
import ru.otus.spring.hw3.game.questions.Question;
import ru.otus.spring.hw3.game.questions.QuestionsDao;

import java.util.regex.Pattern;

@Service
public class Game {
    private static final Pattern NAME_PATTERN = Pattern.compile("\\w+ \\w+");
    private final QuestionsDao questionsDao;
    private final Player player;

    private LocalizedMessageSource messageSource;

    public Game(
            QuestionsDao questionsDao,
            Player player,
            LocalizedMessageSource messageSource
    ) {
        this.questionsDao = questionsDao;
        this.player = player;
        this.messageSource = messageSource;
    }

    public void play() {
        String name = askForName();

        int correctAnswers = 0;
        int numberOfQuestions = questionsDao.getQuestionCount();
        if (numberOfQuestions == 0) {
            player.showResult(getMessage("noQuestions"));
            return;
        }

        for (int questionInd = 0; questionInd < numberOfQuestions; questionInd++) {
            Question question = questionsDao.getQuestion(questionInd);
            String questionText = getMessage(question.getQuestion());
            String questionNumberText = getQuestionNumberText(questionInd);
            String playerAnswer = player.askQuestion(questionNumberText + ". " + questionText);
            if (question.isAnswerCorrect(playerAnswer)) {
                correctAnswers++;
            }
        }

        String result = messageSource.getMessage("gameResult", new Object[]{correctAnswers, name});
        player.showResult(result);
    }

    private String getQuestionNumberText(int questionInd) {
        return messageSource.getMessage("questionNumber", new Object[]{questionInd + 1});
    }

    private String getMessage(String question2) {
        return messageSource.getMessage(question2, null);
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
