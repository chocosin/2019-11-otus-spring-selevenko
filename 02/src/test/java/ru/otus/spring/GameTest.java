package ru.otus.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import ru.otus.spring.game.Game;
import ru.otus.spring.game.GameConfig;
import ru.otus.spring.game.Player;
import ru.otus.spring.game.Question;

import java.util.Locale;

import static org.mockito.Mockito.*;

class GameTest {
    private static final String NAME = "Vladimir Lenin";
    private static final String NAME_QUESTION = "enterYourName";
    private static final String NAME_HINT = "nameHint";

    private MessageSource messageSource;
    private Locale locale;

    @BeforeEach
    private void mockMessageSource() {
        messageSource = mock(MessageSource.class);
        when(messageSource.getMessage(any(), any(), eq(locale))).thenAnswer(
                invocation -> invocation.getArgumentAt(0, String.class)
        );
        when(messageSource.getMessage(eq("gameResult"), any(), eq(locale))).thenAnswer(
                invocation -> {
                    Object[] args = invocation.getArgumentAt(1, Object[].class);
                    int correctNumber = (int) args[0];
                    String name = (String) args[1];
                    return "gameResult " + correctNumber + " " + name;
                }
        );
    }

    @Test
    void testPlayEmptyGame() {
        Player player = mock(Player.class);
        GameConfig gameConfig = mock(GameConfig.class);

        when(player.askQuestion(NAME_QUESTION)).thenReturn(NAME).thenThrow(new IllegalArgumentException());
        when(gameConfig.getQuestionCount()).thenReturn(0);

        new Game(gameConfig, player, locale, messageSource).play();

        verify(player).showResult("noQuestions");
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

        when(player.askQuestion("questionNumber. q1")).thenReturn("wrong");

        new Game(gameConfig, player, locale, messageSource).play();

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
        when(player.askQuestion("questionNumber. q1")).thenReturn("a1");
        when(player.askQuestion("questionNumber. q2")).thenReturn("wrong answer");

        new Game(gameConfig, player, locale, messageSource).play();

        verify(player).askQuestion("questionNumber. q1");
        verify(player).askQuestion("questionNumber. q2");
        verify(player).showResult(correctResult(1, NAME));
        verify(player, never()).showHint(any());
    }

    private static String correctResult(int score, String name) {
        return eq("gameResult " + score + " " + name);
    }
}
