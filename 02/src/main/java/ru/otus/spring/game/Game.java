package ru.otus.spring.game;

import java.text.MessageFormat;
import java.util.regex.Pattern;

public class Game {
    private static final Pattern NAME_PATTERN = Pattern.compile("\\w+ \\w+");
    private final GameConfig gameConfig;
    private final Player player;

    public Game(GameConfig gameConfig, Player player) {
        this.gameConfig = gameConfig;
        this.player = player;
    }

    public void play() {
        String name = askForName();

        int correctAnswers = 0;
        int numberOfQuestions = gameConfig.getQuestionCount();
        if (numberOfQuestions == 0) {
            player.showResult("У нас к вам нет вопросов");
            return;
        }

        for (int questionInd = 0; questionInd < numberOfQuestions; questionInd++) {
            Question question = gameConfig.getQuestion(questionInd);
            String playerAnswer = player.askQuestion(question.getQuestion());
            if (question.isAnswerCorrect(playerAnswer)) {
                correctAnswers++;
            }
        }

        String result = MessageFormat.format("Количество правильных ответов: {0}. Спасибо за игру, {1}.", correctAnswers, name);
        player.showResult(result);
    }

    private String askForName() {
        while (true) {
            String name = player.askQuestion("Введите ваши имя и фамилию (например, \"Владимир Ленин\")").trim();
            if (NAME_PATTERN.matcher(name).matches()) {
                return name;
            }
            player.showHint("Имя должно быть в формате \"Имя Фамилия\"");
        }
    }
}
