package com.example.demo.translate;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TranslateController implements Initializable {
    private String source = "en", target = "vi";

    @FXML

    public JFXButton TranslaterButton, HomeButton, GameButton, SwapButton, TranslateButton, SearchButton;

    public TextArea TranslateTarget, TranslateExplain;

    public Label EnglishLabel, VietnameseLabel;

    public JFXButton CloseButton, MinimizeButton;

    public HBox headBar;

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
    public void initialize() {
        TranslateButton.setDisable(true);
        TranslateTarget.textProperty().addListener((observable, oldValue, newValue) -> {
            TranslateButton.setDisable(newValue.trim().isEmpty());
        });
    }
    @FXML
    protected void onTranslateButtonClick() {
        TranslateAPI translateAPI = new TranslateAPI();
        String texttoTranslate = TranslateTarget.getText();
        String translatedText = translateAPI.translateText(texttoTranslate, source, target);
        TranslateExplain.setText(translatedText);
    }

    @FXML
    protected void onSwapButtonClick() {
        String tempLang = source;
        source = target;
        target = tempLang;

        String EnglishText = EnglishLabel.getText();
        String VietnameseText = VietnameseLabel.getText();
        EnglishLabel.setText(VietnameseText);
        VietnameseLabel.setText(EnglishText);

        TranslateTarget.clear();
        TranslateExplain.clear();
    }

    @FXML
    protected void onHomeButtonClick() {
        try {
            Parent homeView = FXMLLoader.load(getClass().getResource("/com/example/demo/home-view.fxml"));
            Stage stage = (Stage) HomeButton.getScene().getWindow();
            stage.setScene(new Scene(homeView));
            stage.setTitle("Dictionary Application");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Dictionary Application: " + e.getMessage());
        }
    }

    @FXML
    protected void onGameButtonClick() {

        try {
            Parent translateView = FXMLLoader.load(getClass().getResource("/com/example/demo/game-view.fxml"));
            Stage stage = (Stage) GameButton.getScene().getWindow();
            stage.setScene(new Scene(translateView));
            stage.setTitle("Hangman Game");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Translate view: " + e.getMessage());
        }

    }


    @FXML
    protected void onTranslaterButtonClick() {

    }

    @FXML
    protected void onSearchButtonClick() {

        try {
            Parent translateView = FXMLLoader.load(getClass().getResource("/com/example/demo/search-view.fxml"));
            Stage stage = (Stage) SearchButton.getScene().getWindow();
            stage.setScene(new Scene(translateView));
            stage.setTitle("Search");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Translate view: " + e.getMessage());
        }

    }

}