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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class EmptyGameTest {
    private static final String NAME = "Vladimir Lenin";
    private static final String NAME_QUESTION = "Enter your name and surname (ex. Vladimir Lenin)";

    @MockBean
    private Player player;

    @Autowired
    private Game game;

    @TestConfiguration
    public static class EmptyConfiguration {
        @Bean
        public QuestionsDao questionsDao() throws IOException {
            return new CSVQuestionsDao(new ClassPathResource("empty.csv"));
        }
    }

    @Test
    void testPlayEmptyGame() {
        when(player.askQuestion(NAME_QUESTION)).thenReturn(NAME).thenThrow(new IllegalArgumentException());
        game.play();
        verify(player).showResult("We don't have any questions");
    }
}
