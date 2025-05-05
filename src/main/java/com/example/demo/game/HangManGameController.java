package com.example.demo.game;

import com.example.demo.common.TransitionController;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class HangManGameController extends TransitionController {

    @FXML
    public JFXButton GameButton;

    public Text wordDisplay, messageDisplay;

    public JFXButton restartButton;

    public Circle head;

    public Line body, leftArm, rightArm, leftLeg, rightLeg;

    public List<javafx.scene.Node> bodyParts;


    @FXML
    private JFXButton btnQ, btnW, btnE, btnR, btnT, btnY, btnU, btnI, btnO, btnP,
            btnA, btnS, btnD, btnF, btnG, btnH, btnJ, btnK, btnL,
            btnZ, btnX, btnC, btnV, btnB, btnN, btnM;


    private HangmanGame game;
    private Map<Character, JFXButton> letterButtons;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        game = new HangmanGame();

        bodyParts = new ArrayList<>();
        bodyParts.add(head);
        bodyParts.add(body);
        bodyParts.add(leftArm);
        bodyParts.add(rightArm);
        bodyParts.add(leftLeg);
        bodyParts.add(rightLeg);
        bodyParts.forEach(part -> {
            part.setOpacity(0);
            fadeStickmanPart(part);
        });

        initializeGame();
        slideInTransition(wordDisplay, false);
        slideInTransition(messageDisplay, true);
        animateLetterButtons();
    }

    private void fadeStickmanPart(Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(2);
        fadeTransition.setAutoReverse(true);

        node.setOpacity(0.0);
        fadeTransition.play();
    }

    private void animateLetterButtons() {
        letterButtons.forEach((letter, button) -> {
            button.setOpacity(0);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(200), button);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            int index = letter - 'A';
            fadeIn.setDelay(Duration.millis(50 * index));
            fadeIn.play();
        });
    }

    private void initializeGame() {
        letterButtons = new HashMap<>();

        letterButtons.put('Q', btnQ);
        letterButtons.put('W', btnW);
        letterButtons.put('E', btnE);
        letterButtons.put('R', btnR);
        letterButtons.put('T', btnT);
        letterButtons.put('Y', btnY);
        letterButtons.put('U', btnU);
        letterButtons.put('I', btnI);
        letterButtons.put('O', btnO);
        letterButtons.put('P', btnP);
        letterButtons.put('A', btnA);
        letterButtons.put('S', btnS);
        letterButtons.put('D', btnD);
        letterButtons.put('F', btnF);
        letterButtons.put('G', btnG);
        letterButtons.put('H', btnH);
        letterButtons.put('J', btnJ);
        letterButtons.put('K', btnK);
        letterButtons.put('L', btnL);
        letterButtons.put('Z', btnZ);
        letterButtons.put('X', btnX);
        letterButtons.put('C', btnC);
        letterButtons.put('V', btnV);
        letterButtons.put('B', btnB);
        letterButtons.put('N', btnN);
        letterButtons.put('M', btnM);

        letterButtons.forEach((letter, button) -> {
            button.setOnAction(e -> handleLetterClick(letter, button));
        });

        restartButton.setOnAction(e -> restartGame());
        updateDisplay();
    }

    private void handleLetterClick(Character letter, JFXButton button) {
        if (!game.isGameOver()) {
            boolean correct = game.makeGuess(letter);

            button.setDisable(true);
            if (correct) {
                button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            } else {
                button.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");
                int incorrectGuesses = game.getIncorrectGuesses().size() - 1;
                if (incorrectGuesses >= 0 && incorrectGuesses < bodyParts.size()) {
                    bodyParts.get(incorrectGuesses).setOpacity(1);
                }
            }

            updateDisplay();
        }
    }

    private void updateDisplay() {
        StringBuilder displayText = new StringBuilder();
        String currentWord = game.getCurrentWord();
        for (int i = 0; i < currentWord.length(); i++) {
            displayText.append(currentWord.charAt(i)).append(" ");
        }
        wordDisplay.setText(displayText.toString().trim());
        wordDisplay.setStyle("-fx-font-size: 40px; -fx-font-family: monospace; -fx-letter-spacing: 1px;");

        if (game.isGameOver()) {
            if (game.isWon()) {
                messageDisplay.setText("Congratulations! You won!");
                messageDisplay.setFill(Color.GREEN);
            } else {
                messageDisplay.setText("Game Over! The word was: " + game.getActualWord());
                messageDisplay.setFill(Color.RED);
            }
        }
    }

    private void restartGame() {
        game.reset();
        letterButtons.values().forEach(button -> {
            button.setDisable(false);
            button.setStyle("");
        });
        messageDisplay.setText("");
        bodyParts.forEach(part -> part.setOpacity(0));
        updateDisplay();
    }

    @FXML
    protected void onGameButtonClick() {
    }

    @FXML
    protected void onRestartButtonClick() {
        game.reset();
        letterButtons.values().forEach(button -> {
            button.setDisable(false);
            button.setStyle("");
        });
        messageDisplay.setText("");
        updateDisplay();
    }

}
