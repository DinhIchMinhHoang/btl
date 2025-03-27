package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeViewController {

    @FXML
    public HBox TranslaterButton, HomeButton, GameButton, SearchButton;

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

        try {
            Parent translateView = FXMLLoader.load(getClass().getResource("game-view.fxml"));
            Stage stage = (Stage) GameButton.getScene().getWindow();
            stage.setScene(new Scene(translateView));
            stage.setTitle("Hangman Game");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Translate view: " + e.getMessage());
        }

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
}