package ru.otus.spring.game;

public class Question {
    private final String question;
    private final String answer;

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer.trim();
    }

    public String getQuestion() {
        return question;
    }

    public boolean isAnswerCorrect(String answer) {
        return this.answer.equalsIgnoreCase(answer.trim());
    }
}
