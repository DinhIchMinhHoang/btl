package com.example.demo.game;
import java.util.List;

public class HangmanGame extends AbstracGame {
    public HangmanGame() {
        initializeGame();
        selectRandomWord("demo/src/main/resources/data/gameWords.txt"); // Correct file path

    }

    @Override
    public void start() {
        System.out.println("Hangman Game started!");
    }

    @Override
    public void reset() {
        incorrectGuesses.clear();
        remainingGuesses = MAX_TRIES;
        gameOver = false;
        selectRandomWord("demo/src/main/resources/data/gameWords.txt");
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public String getInstructions() {
        return "Guess the word by suggesting letters";
    }

    @Override
    public void updateDisplay() {
        System.out.println("Updating Hangman display...");
    }

    @Override
    public boolean makeGuess(char letter) {
        if (gameOver) {
            return false;
        }
        letter = Character.toUpperCase(letter);
        boolean correctGuess = false;
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == letter) {
                guessedLetters[i] = letter;
                correctGuess = true;
            }
        }
        if (!correctGuess) {
            if (!incorrectGuesses.contains(letter)) {
                incorrectGuesses.add(letter);
                remainingGuesses--;
            }
        }
        checkGameStatus();
        return correctGuess;
    }
    @Override
    public String getCurrentWord() {
        return String.valueOf(guessedLetters);
    }

    @Override
    public String getActualWord() {
        return wordToGuess;
    }

    @Override
    public List<Character> getIncorrectGuesses() {
        return incorrectGuesses;
    }

    @Override
    public int getRemainingTries() {
        return remainingGuesses;
    }

    @Override
    public boolean isWon() {
        return gameOver && !String.valueOf(guessedLetters).contains("_");
    }
}
