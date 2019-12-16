package ru.otus.spring.hw4

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import ru.otus.spring.hw4.questions.QuestionsDAO

@SpringBootTest
@ActiveProfiles("test")
internal class GameTest {
    @MockBean
    lateinit var questionsDAO: QuestionsDAO

    @Autowired
    @Qualifier("testGame")
    lateinit var game: Game

    @Test
    @DirtiesContext
    fun `should login with proper name`() {
        assertThat(game.isLoggedIn()).matches { !it.isAvailable }
        val okMsg = game.login("John Black")
        assertThat(okMsg).contains("successful")
        assertThat(game.isLoggedIn()).matches { it.isAvailable }
    }

    @Test
    @DirtiesContext
    fun `game of one correct question`() {
        `when`(questionsDAO.getQuestionCount()).thenReturn(1)
        `when`(questionsDAO.getQuestion(0)).thenReturn(Question("q1", "a1"))

        assertThat(game.getScore()).isEqualTo(score(0, 0))

        val questionText = game.nextQuestion()
        assertThat(questionText).isEqualTo("question1?")

        val answerIndicator = game.answer("a1")
        assertThat(answerIndicator).isEqualTo("Correct")

        assertThat(game.getScore()).isEqualTo(score(1, 1))
    }

    @Test
    @DirtiesContext
    fun `should answer no question after last question`() {
        `when`(questionsDAO.getQuestionCount()).thenReturn(1)
        `when`(questionsDAO.getQuestion(0)).thenReturn(Question("q1", "a1"))

        assertThat(game.getScore()).isEqualTo(score(0, 0))

        assertThat(game.nextQuestion()).isEqualTo("question1?")
        assertThat(game.answer("a1")).isEqualTo("Correct")
        assertThat(game.getScore()).isEqualTo(score(1, 1))

        assertThat(game.nextQuestion()).isEqualTo("No more question, to see your result use command 's'")
        assertThat(game.nextQuestion()).isEqualTo("No more question, to see your result use command 's'")
        assertThat(game.answer("answer")).isEqualTo("Ask question first, command 'q'")
    }

    @Test
    @DirtiesContext
    fun `game of two correct and incorrect questions`() {
        `when`(questionsDAO.getQuestionCount()).thenReturn(2)
        `when`(questionsDAO.getQuestion(0)).thenReturn(Question("q1", "a1"))
        `when`(questionsDAO.getQuestion(1)).thenReturn(Question("q2", "a2"))

        assertThat(game.getScore()).isEqualTo(score(0, 0))

        assertThat(game.nextQuestion()).isEqualTo("question1?")
        assertThat(game.answer("a1")).isEqualTo("Correct")
        assertThat(game.getScore()).isEqualTo(score(1, 1))

        assertThat(game.nextQuestion()).isEqualTo("question2?")
        assertThat(game.answer("wrong")).isEqualTo("Incorrect")
        assertThat(game.getScore()).isEqualTo(score(1, 2))
    }

    private fun score(correct: Int, total: Int) = "Your current score is: $correct out of $total"

    @Configuration
    class TestConfiguration {
        @Bean
        fun user() = User()

        @Bean
        fun applicationProperties(@Value("\${app.language}") lang: String) =
                ApplicationProperties().also {
                    it.language = lang
                }

        @Bean
        fun localizedMessageSource(props: ApplicationProperties): LocalizedMessageSource {
            val ms = ReloadableResourceBundleMessageSource()
            ms.setDefaultEncoding("UTF-8")
            ms.setBasename("testQuestions")

            return LocalizedMessageSource(ms, props)
        }

        @Bean
        fun testGame(
                localizedMessageSource: LocalizedMessageSource,
                questionsDAO: QuestionsDAO,
                user: User
        ): Game {
            return Game(localizedMessageSource, questionsDAO, user)
        }
    }
}
