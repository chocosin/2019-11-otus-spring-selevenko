package ru.otus.spring.game;

import java.util.Scanner;

public class STDIOPlayer implements Player, AutoCloseable {
    private final Scanner scanner;

    public STDIOPlayer() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String askQuestion(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }

    @Override
    public void showHint(String hint) {
        System.out.println(hint);
    }

    @Override
    public void showResult(String result) {
        System.out.println(result);
        close();
    }

    @Override
    public void close() {
        scanner.close();
    }
}
