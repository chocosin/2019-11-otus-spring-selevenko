package ru.otus.spring;

import org.junit.jupiter.api.Test;
import ru.otus.spring.game.Game;
import ru.otus.spring.game.GameConfig;
import ru.otus.spring.game.Player;
import ru.otus.spring.game.Question;

import java.text.MessageFormat;

import static org.mockito.Mockito.*;

class GameTest {
    private static final String NAME = "Vladimir Lenin";
    private static final String NAME_QUESTION = "Введите ваши имя и фамилию (например, \"Владимир Ленин\")";
    private static final String NAME_HINT = "Имя должно быть в формате \"Имя Фамилия\"";

    @Test
    void testPlayEmptyGame() {
        Player player = mock(Player.class);
        GameConfig gameConfig = mock(GameConfig.class);
        when(player.askQuestion(NAME_QUESTION)).thenReturn(NAME).thenThrow(new IllegalArgumentException());
        when(gameConfig.getQuestionCount()).thenReturn(0);
        when(gameConfig.getQuestionCount()).thenReturn(0);

        new Game(gameConfig, player).play();

        verify(player).showResult("У нас к вам нет вопросов");
    }

    @Test
    void testPlayerEntersEmptyNameAndInvalidName() {
        Player player = mock(Player.class);

        when(player.askQuestion(NAME_QUESTION))
                .thenReturn("   ")
                .thenReturn("John")
                .thenReturn(NAME)
                .thenThrow(new IllegalArgumentException());

        GameConfig gameConfig = mock(GameConfig.class);
        when(gameConfig.getQuestionCount()).thenReturn(1);
        when(gameConfig.getQuestion(0)).thenReturn(new Question("q1", "a1"));

        when(player.askQuestion("q1")).thenReturn("wrong");

        new Game(gameConfig, player).play();

        verify(player, times(3)).askQuestion(NAME_QUESTION);
        verify(player, times(2)).showHint(NAME_HINT);
        verify(player).showResult(correctResult(0, NAME));
    }

    @Test
    void testGameWithTwoQuestions() {
        GameConfig gameConfig = mock(GameConfig.class);
        when(gameConfig.getQuestionCount()).thenReturn(2);
        when(gameConfig.getQuestion(0)).thenReturn(new Question("q1", "a1"));
        when(gameConfig.getQuestion(1)).thenReturn(new Question("q2", "a2"));

        Player player = mock(Player.class);
        when(player.askQuestion(NAME_QUESTION)).thenReturn(NAME);
        when(player.askQuestion("q1")).thenReturn("a1");
        when(player.askQuestion("q2")).thenReturn("wrong answer");

        new Game(gameConfig, player).play();

        verify(player).askQuestion("q1");
        verify(player).askQuestion("q2");
        verify(player).showResult(correctResult(1, NAME));
        verify(player, never()).showHint(any());
    }

    private static String correctResult(int score, String name) {
        return eq(MessageFormat.format("Количество правильных ответов: {0}. Спасибо за игру, {1}.", score, name));
    }
}
