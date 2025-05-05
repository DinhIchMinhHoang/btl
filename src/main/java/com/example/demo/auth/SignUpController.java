package com.example.demo.auth;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {

    @FXML
    public JFXButton SwitchLoginButton, RegisteButton;

    public JFXTextArea Username, Password, ConfirmPassword;

    public Label WarningText;

    private UserManager userManager;

    public void initialize() {

        userManager = new UserManager();

        RegisteButton.setDisable(true);

        Username.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputs();
        });

        Password.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputs();
        });

        ConfirmPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputs();
        });
    }

    protected void validateInputs() {
        boolean isValid = !Username.getText().trim().isEmpty() &&
                !Password.getText().trim().isEmpty() &&
                !ConfirmPassword.getText().trim().isEmpty();

        RegisteButton.setDisable(!isValid);
    }

    @FXML
    protected void onSwitchLoginButtonClick() {
        try {
            Parent homeView = FXMLLoader.load(getClass().getResource("/com/example/demo/login-view.fxml"));
            Stage stage = (Stage) SwitchLoginButton.getScene().getWindow();
            stage.setScene(new Scene(homeView));
            stage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Dictionary Application: " + e.getMessage());
        }
    }

    @FXML
    protected void onResgisterButtonClick() {

        String username = Username.getText().trim();
        String password = Password.getText().trim();
        String confirmPassword = ConfirmPassword.getText().trim();

        if(!password.equals(confirmPassword)) {
            WarningText.setText("Passwords do not match");
            return;
        }

        User newUser = new User(username, password);

        if(userManager.addUser(newUser)) {
            try {
                Parent homeView = FXMLLoader.load(getClass().getResource("/com/example/demo/login-view.fxml"));
                Stage stage = (Stage) RegisteButton.getScene().getWindow();
                stage.setScene(new Scene(homeView));
                stage.setTitle("Login");
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error loading Dictionary Application: " + e.getMessage());
            }
        } else {
            WarningText.setText("Username are already taken, please try another name");
        }
    }

}
