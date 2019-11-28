package ru.otus.spring;

import org.junit.jupiter.api.Test;
import ru.otus.spring.game.Game;
import ru.otus.spring.game.GameConfig;
import ru.otus.spring.game.Player;
import ru.otus.spring.game.Question;
import static org.mockito.Mockito.*;

class GameTest {

    @Test
    void testPlayEmptyGame() {
        Player player = mock(Player.class);
        GameConfig gameConfig = mock(GameConfig.class);
        when(gameConfig.getQuestionCount()).thenReturn(0);

        new Game(gameConfig, player).play();

        verify(player).showResult("У нас к вам нет вопросов");
    }

    @Test
    public void testGameWithTwoQuestions() {
        GameConfig gameConfig = mock(GameConfig.class);
        when(gameConfig.getQuestionCount()).thenReturn(2);
        when(gameConfig.getQuestion(0)).thenReturn(new Question("q1", "a1"));
        when(gameConfig.getQuestion(1)).thenReturn(new Question("q2", "a2"));

        Player player = mock(Player.class);
        when(player.askQuestion("q1")).thenReturn("a1");
        when(player.askQuestion("q2")).thenReturn("wrong answer");

        new Game(gameConfig, player).play();

        verify(player).askQuestion("q1");
        verify(player).askQuestion("q2");
        verify(player).showResult("Количество правильных ответов: " + 1);
    }
}
