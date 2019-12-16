package ru.otus.spring.hw4

import org.springframework.shell.Availability
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellMethodAvailability
import ru.otus.spring.hw4.questions.QuestionsDAO

@ShellComponent
class Game(
        val messageSource: LocalizedMessageSource,
        val questionsDAO: QuestionsDAO,
        val user: User
) {

    private var currentQuestion: Question? = null
    private var questionNumber = 0
    private var score: Int = 0

    @ShellMethod(value = "ask next question", key = ["q", "question"])
    @ShellMethodAvailability("isLoggedIn")
    fun nextQuestion(): String {
        if (questionsDAO.getQuestionCount() <= questionNumber) {
            return messageSource.getMessage("noQuestions")
        }

        val next = questionsDAO.getQuestion(questionNumber)
        currentQuestion = next
        return messageSource.getMessage(next.question)
    }

    @ShellMethod(value = "submit question answer", key = ["a", "answer"])
    @ShellMethodAvailability("isLoggedIn")
    fun answer(answer: String): String {
        val correct = currentQuestion.let { q ->
            if (q == null) return messageSource.getMessage("askQuestionFirst")
            q.isAnswerCorrect(answer)
        }

        questionNumber++
        currentQuestion = null

        return if (correct) {
            score++
            messageSource.getMessage("correct")
        } else {
            messageSource.getMessage("incorrect")
        }
    }

    @ShellMethod(value = "login into testing", key = ["l", "login"])
    fun login(name: String = ""): String {
        if (user.isLoggedIn()) return messageSource.getMessage("alreadyLoggedIn", user.fullName)

        val ok = user.logIn(name.trim())
        return if (!ok) {
            messageSource.getMessage("loginFailed")
        } else {
            messageSource.getMessage("loginSuccess")
        }
    }

    @ShellMethod(value = "Get current score", key = ["s", "score"])
    @ShellMethodAvailability("isLoggedIn")
    fun getScore(): String = messageSource.getMessage("currentScore", score, questionNumber)

    fun isLoggedIn(): Availability =
            if (user.isLoggedIn()) Availability.available()
            else Availability.unavailable(messageSource.getMessage("loginFirst"))
}
