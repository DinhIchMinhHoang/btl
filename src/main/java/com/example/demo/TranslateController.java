package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TranslateController {
    private String source = "en", target = "vi";

    @FXML
    public Button TranslateButton, SwapButton;

    public HBox TranslaterButton, HomeButton, GameButton;

    public TextArea TranslateTarget, TranslateExplain;

    public Label EnglishLabel, VietnameseLabel;

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
            Parent homeView = FXMLLoader.load(getClass().getResource("home-view.fxml"));
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

    }


    @FXML
    protected void onTranslaterButtonClick() {

    }

}