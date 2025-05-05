package com.example.demo.auth;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    public JFXButton LoginButton, SignUpButton;

    public JFXTextField UserName;

    public JFXPasswordField Password;

    public Label Wrong;

    private UserManager userManager;

    public void initialize() {

        userManager = new UserManager();

        LoginButton.setDisable(true);

        UserName.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputs();
        });

        Password.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputs();
        });
    }

    protected void validateInputs() {
        boolean isValid = !UserName.getText().trim().isEmpty() &&
                !Password.getText().trim().isEmpty();

        LoginButton.setDisable(!isValid);
    }



    @FXML
    protected void onSignUpButtonClick() {
        try {
            Parent homeView = FXMLLoader.load(getClass().getResource("/com/example/demo/signup-view.fxml"));
            Stage stage = (Stage) SignUpButton.getScene().getWindow();
            stage.setScene(new Scene(homeView));
            stage.setTitle("Sign Up");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Dictionary Application: " + e.getMessage());
        }
    }

    @FXML
    protected void onLoginButtonClick() {
        String username = UserName.getText().trim();
        String password = Password.getText().trim();

        System.out.println("Attempting login with:");
        System.out.println("Username: '" + username + "'");
        System.out.println("Password: '" + password + "'");

        if (userManager.verifyUser(username, password)) {
            try {
                Parent homeView = FXMLLoader.load(getClass().getResource("/com/example/demo/home-view.fxml"));
                Stage stage = (Stage) LoginButton.getScene().getWindow();
                stage.setScene(new Scene(homeView));
                stage.setTitle("Dictionary Application");
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error loading Dictionary Application: " + e.getMessage());
            }
        } else {
            Wrong.setText("Invalid Username or Password");
        }
    }


}

