package com.example.demo.translate;

import com.example.demo.common.BaseController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


public class TranslateController extends BaseController {
    private String source = "en", target = "vi";

    @FXML

    public JFXButton SwapButton, TranslateButton;

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
    protected void onTranslaterButtonClick() {
    }

}