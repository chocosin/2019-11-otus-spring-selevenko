package ru.otus.spring.game;

public interface GameConfig {
    Question getQuestion(int num);

    int getQuestionCount();
}
