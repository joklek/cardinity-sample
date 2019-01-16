package com.joklek.cardintitysample.window;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Scanner;

public abstract class AbstractWindow {

    @Autowired
    private Map<String, AbstractWindow> windows;

    protected final Scanner scanner;

    public AbstractWindow() {
        scanner = new Scanner(System.in);
    }

    public abstract void render(Map<String, Object> args);

    protected abstract String getTitle();

    protected void switchWindow(String window, Map<String, Object> args) {
        if (windows.containsKey(window)) {
            windows.get(window).render(args);
        }
        else {
            System.err.printf("Can't find window '%s'%n", window);
        }
    }

    protected int readChoice() {
        return scanner.nextInt();
    }

    protected String readInput(){
        scanner.nextLine();
        return scanner.nextLine();
    }
}

