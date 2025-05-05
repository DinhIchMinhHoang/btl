package com.example.demo.game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

abstract class AbstracGame {
    protected String wordToGuess;
    protected char[] guessedLetters;
    protected List<Character> incorrectGuesses;
    protected int remainingGuesses;
    protected boolean gameOver;
    protected static final int MAX_TRIES = 6;

    public abstract void start();
    public abstract void reset();
    public abstract boolean isGameOver();
    public abstract String getInstructions();
    public abstract void updateDisplay();
    public abstract boolean makeGuess(char letter);
    public abstract String getCurrentWord();
    public abstract String getActualWord();
    public abstract List<Character> getIncorrectGuesses();
    public abstract int getRemainingTries();
    public abstract boolean isWon();


    protected void initializeGame() {
        System.out.println("Initializing game...");
        incorrectGuesses = new ArrayList<>();
        remainingGuesses = MAX_TRIES;
        gameOver = false;
    }

    protected void selectRandomWord(String filePath) {
        try {
            List<String> words = Files.readAllLines(Paths.get(filePath));
            wordToGuess = words.get(new Random().nextInt(words.size())).trim().toUpperCase();
            guessedLetters = new char[wordToGuess.length()];
            Arrays.fill(guessedLetters, '_');
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading words file : " + e.getMessage());
        }
    }

    protected void checkGameStatus() {
        if (remainingGuesses <= 0) {
            gameOver = true;
        } else if (!String.valueOf(guessedLetters).contains("_")) {
            gameOver = true;
        }
    }

}


