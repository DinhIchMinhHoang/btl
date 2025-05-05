package com.example.demo.common;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class BaseController implements Initializable {

    @FXML
    protected JFXButton TranslaterButton, HomeButton, GameButton, SearchButton;

    @FXML
    protected JFXButton CloseButton, MinimizeButton;

    @FXML
    protected HBox headBar;

    protected double xOffset = 0, yOffset = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupWindowDragging();
    }

    private void setupWindowDragging() {
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
    protected void onMinimizeButtonClick() {
        Stage stage = (Stage) MinimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    protected void onCloseButtonClick() {
        Stage stage = (Stage) CloseButton.getScene().getWindow();
        stage.close();
    }

    protected void navigateToView(String fxmlPath, String title) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) HomeButton.getScene().getWindow();
            stage.setScene(new Scene(view));
            stage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading " + title + " view: " + e.getMessage());
        }
    }

    @FXML
    protected void onTranslaterButtonClick() {
        navigateToView("/com/example/demo/translate-view.fxml", "Translator");
    }

    @FXML
    protected void onHomeButtonClick() {
        navigateToView("/com/example/demo/home-view.fxml", "Dictionary Application");
    }

    @FXML
    protected void onGameButtonClick() {
        navigateToView("/com/example/demo/game-view.fxml", "Hangman Game");
    }

    @FXML
    protected void onSearchButtonClick() {
        navigateToView("/com/example/demo/search-view.fxml", "Search");
    }
}
