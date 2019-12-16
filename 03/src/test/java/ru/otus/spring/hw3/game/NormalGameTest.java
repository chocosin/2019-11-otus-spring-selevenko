package ru.otus.spring.hw3.game;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring.hw3.game.questions.QuestionsDao;
import ru.otus.spring.hw3.game.questions.csv.CSVQuestionsDao;

import java.io.IOException;
import java.text.MessageFormat;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class NormalGameTest {
    private static final String NAME = "Vladimir Lenin";
    private static final String NAME_QUESTION = "Enter your name and surname (ex. Vladimir Lenin)";
    private static final String NAME_HINT = "Name must have the following format \"Firstname Lastname\"";

    @MockBean
    private Player player;

    @Autowired
    private Game game;

    @TestConfiguration
    public static class EmptyConfiguration {
        @Bean
        public QuestionsDao questionsDao() throws IOException {
            return new CSVQuestionsDao(new ClassPathResource("test1.csv"));
        }
    }

    @Test
    void testPlayerAnswersOneQuestionWrong() {
        when(player.askQuestion(NAME_QUESTION))
                .thenReturn("   ")
                .thenReturn("John")
                .thenReturn(NAME)
                .thenThrow(new IllegalArgumentException());

        when(player.askQuestion("Question number 1. testQ1")).thenReturn("444");
        when(player.askQuestion("Question number 2. testQ2")).thenReturn("444");

        game.play();


        verify(player, times(3)).askQuestion(NAME_QUESTION);
        verify(player, times(2)).showHint(NAME_HINT);
        verify(player).showResult(correctResult(1, NAME));
    }

    @Test
    void testPlayerAnswersBothQuestionsRight() {
        when(player.askQuestion(NAME_QUESTION))
                .thenReturn(NAME)
                .thenThrow(new IllegalArgumentException());

        when(player.askQuestion("Question number 1. testQ1")).thenReturn("444");
        when(player.askQuestion("Question number 2. testQ2")).thenReturn("222");

        game.play();


        verify(player, times(1)).askQuestion(NAME_QUESTION);
        verify(player).showResult(correctResult(2, NAME));
    }

    private static String correctResult(int score, String name) {
        return eq(MessageFormat.format("Number of correct answers: {0}. Thanks for playing, {1}.", score, name));
    }

}
