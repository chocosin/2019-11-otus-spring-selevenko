package ru.otus.spring.hw4

data class Question(
        val question: String,
        val answer: String
) {
    fun isAnswerCorrect(answer: String) = this.answer == answer
}
