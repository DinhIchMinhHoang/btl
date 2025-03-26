package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class TranslateController {
    private String source = "en", target = "vi";

    @FXML
    public Button TranslateButton, SwapButton;

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
}