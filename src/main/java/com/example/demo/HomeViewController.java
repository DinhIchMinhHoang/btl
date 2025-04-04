package com.example.demo;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javax.print.DocFlavor;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {

    @FXML
    public JFXButton TranslaterButton, HomeButton, GameButton, SearchButton;

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