package com.example.demo.translate;

import com.example.demo.common.BaseController;
import com.example.demo.common.TransitionController;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;


public class TranslateController extends TransitionController {
    private String source = "en", target = "vi";

    @FXML

    public JFXButton SwapButton, TranslateButton;

    public TextArea TranslateTarget, TranslateExplain;

    public Label EnglishLabel, VietnameseLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        // Get the translation text areas
        Node sourceText = TranslateTarget;
        Node targetText = TranslateExplain;

        // Apply animations
        slideInTransition(sourceText, false);
        slideInTransition(targetText, true);

        // Animate the swap button
        Node swapButton = SwapButton;
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), swapButton);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setDelay(Duration.millis(300));

        swapButton.setOpacity(0.0);
        fadeIn.play();
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
    protected void onTranslaterButtonClick() {
    }

}