package ru.otus.spring.hw3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.hw3.game.Game;

@SpringBootApplication
public class Hw3Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Hw3Application.class, args);
        context.getBean(Game.class).play();
    }

}
