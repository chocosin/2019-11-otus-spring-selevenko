package ru.otus.spring.hw3.game.questions;

public interface QuestionsDao {
    Question getQuestion(int num);

    int getQuestionCount();
}
