package com.example.demo;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HangManGameController implements Initializable {

    @FXML
    public JFXButton TranslaterButton, HomeButton, GameButton, SearchButton;

    public JFXButton CloseButton, MinimizeButton;

    public HBox headBar;

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
    private double xOffset = 0, yOffset = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        headBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        headBar.setOnMouseDragged(event -> {
            Stage stage = (Stage) headBar.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        game = new HangmanGame();
        initializeGame();

        bodyParts = new ArrayList<>();
        bodyParts.add(head);
        bodyParts.add(body);
        bodyParts.add(leftArm);
        bodyParts.add(rightArm);
        bodyParts.add(leftLeg);
        bodyParts.add(rightLeg);

        bodyParts.forEach(part -> part.setOpacity(0));

        game = new HangmanGame();
        initializeGame();

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
        wordDisplay.setStyle("-fx-font-size: 40px; -fx-font-family: monospace; -fx-letter-spacing: 10px;");

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
    protected void onMinimizeButtonClick(){

        Stage stage = (Stage) MinimizeButton.getScene().getWindow();
        stage.setIconified(true);

    }

    @FXML
    protected void onCloseButtonClick(){

        Stage stage = (Stage) CloseButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    protected void onTranslaterButtonClick() {
        try {
            Parent translateView = FXMLLoader.load(getClass().getResource("translate-view.fxml"));
            Stage stage = (Stage) TranslaterButton.getScene().getWindow();
            stage.setScene(new Scene(translateView));
            stage.setTitle("Translator");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Translate view: " + e.getMessage());
        }
    }

    @FXML
    protected void onHomeButtonClick() {
        try {
            Parent translateView = FXMLLoader.load(getClass().getResource("home-view.fxml"));
            Stage stage = (Stage) HomeButton.getScene().getWindow();
            stage.setScene(new Scene(translateView));
            stage.setTitle("Dictionary Application");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Translate view: " + e.getMessage());
        }
    }

    @FXML
    protected void onGameButtonClick() {


    }

    @FXML
    protected void onSearchButtonClick() {

        try {
            Parent translateView = FXMLLoader.load(getClass().getResource("search-view.fxml"));
            Stage stage = (Stage) SearchButton.getScene().getWindow();
            stage.setScene(new Scene(translateView));
            stage.setTitle("Search");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Translate view: " + e.getMessage());
        }

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
