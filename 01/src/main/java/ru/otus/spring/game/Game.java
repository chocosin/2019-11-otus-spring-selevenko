package ru.otus.spring.game;

public class Game {
    private final GameConfig gameConfig;
    private final Player player;

    public Game(GameConfig gameConfig, Player player) {
        this.gameConfig = gameConfig;
        this.player = player;
    }

    public void play() {
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

        String result = "Количество правильных ответов: " + correctAnswers;
        player.showResult(result);
    }
}
