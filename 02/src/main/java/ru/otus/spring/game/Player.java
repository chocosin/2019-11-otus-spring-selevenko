package ru.otus.spring.game;

public interface Player {
    String askQuestion(String question);

    void showHint(String hint);
    void showResult(String result);
}
