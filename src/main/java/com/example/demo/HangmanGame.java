package com.example.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HangmanGame {
    private String word;
    private char[] guessedLetters;
    private List<Character> incorrectGuesses;
    private int remainingTries;
    private boolean gameOver;
    private static final int MAX_TRIES = 6;

    public HangmanGame() {
        this.incorrectGuesses = new ArrayList<>();
        this.remainingTries = MAX_TRIES;
        this.gameOver = false;
        selectRandomWord();
    }

    private void selectRandomWord() {
        try {
            List<String> words = Files.readAllLines(Paths.get("demo/src/main/resources/gameWords.txt"));
            word = words.get(new Random().nextInt(words.size())).trim().toUpperCase();
            guessedLetters = new char[word.length()];
            Arrays.fill(guessedLetters, '_');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean makeGuess(char letter) {
        if (gameOver) {
            return false;
        }

        letter = Character.toUpperCase(letter);
        boolean correctGuess = false;

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                guessedLetters[i] = letter;
                correctGuess = true;
            }
        }

        if (!correctGuess) {
            if (!incorrectGuesses.contains(letter)) {
                incorrectGuesses.add(letter);
                remainingTries--;
            }
        }

        checkGameStatus();
        return correctGuess;
    }

    private void checkGameStatus() {
        if (remainingTries <= 0) {
            gameOver = true;
        } else if (!String.valueOf(guessedLetters).contains("_")) {
            gameOver = true;
        }
    }

    public void reset() {
        incorrectGuesses.clear();
        remainingTries = MAX_TRIES;
        gameOver = false;
        selectRandomWord();
    }

    public String getCurrentWord() {
        return String.valueOf(guessedLetters);
    }

    public String getActualWord() {
        return word;
    }

    public List<Character> getIncorrectGuesses() {
        return incorrectGuesses;
    }

    public int getRemainingTries() {
        return remainingTries;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isWon() {
        return gameOver && !String.valueOf(guessedLetters).contains("_");
    }

}
