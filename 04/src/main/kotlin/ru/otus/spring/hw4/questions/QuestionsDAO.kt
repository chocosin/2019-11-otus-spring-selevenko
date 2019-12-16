package ru.otus.spring.hw4.questions

import ru.otus.spring.hw4.Question

interface QuestionsDAO {
    fun getQuestionCount(): Int
    fun getQuestion(num: Int): Question
}