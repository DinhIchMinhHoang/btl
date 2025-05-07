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

/**
 * controller cho signup-view.fxml
 */
public class SignUpController {

    @FXML
    public JFXButton SwitchLoginButton, RegisteButton;

    public JFXTextArea Username, Password, ConfirmPassword;

    public Label WarningText;

    private UserManager userManager;

    /**
     * khoi tao controller va thiet lap cac listener cho cac textarea
     * goi tu dong sau khi file fxml duoc tai
     */
    public void initialize() {

        userManager = new UserManager();//khoi tao quan ly nguoi dung

        RegisteButton.setDisable(true);//disable register button

        // them listener cho textarea username
        Username.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputs();
        });

        // them listener cho textarea password
        Password.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputs();
        });

        // them listener cho textarea confirm password
        ConfirmPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputs();
        });
    }

    /**
     * xac thuc du lieu nhap vao tu nguoi dung
     * kiem tra xem tat ca cac textarea co du lieu khong
     * neu hop le register button se enable
     */
    protected void validateInputs() {
        //kiem tra xem textarea co du lieu khong
        boolean isValid = !Username.getText().trim().isEmpty() &&
                !Password.getText().trim().isEmpty() &&
                !ConfirmPassword.getText().trim().isEmpty();

        //enable/disable button
        RegisteButton.setDisable(!isValid);
    }

    /**
     * chuyen nguoi dung den man hinh dang nhap
     */
    @FXML
    protected void onSwitchLoginButtonClick() {
        try {
            //tai va chuyen den man hinh dang nhap
            Parent homeView = FXMLLoader.load(getClass().getResource("/com/example/demo/login-view.fxml"));
            Stage stage = (Stage) SwitchLoginButton.getScene().getWindow();
            stage.setScene(new Scene(homeView));
            stage.setTitle("Login");
        } catch (IOException e) {
            //bao loi khi tai scene
            e.printStackTrace();
            System.err.println("Error loading Dictionary Application: " + e.getMessage());
        }
    }

    /**
     * kiem tra xem mat khau da chinh xac chua
     * tao user va chuyen den scene dang nhap
     */
    @FXML
    protected void onResgisterButtonClick() {

        //lay data tren text area
        String username = Username.getText().trim();
        String password = Password.getText().trim();
        String confirmPassword = ConfirmPassword.getText().trim();

        //kiem tra mat khau va confirm mat khau co khop khong
        if(!password.equals(confirmPassword)) {
            WarningText.setText("Passwords do not match");
            return;
        }

        //tao doi tuong nguoi dung moi
        User newUser = new User(username, password);

        //them user moi vao file
        if(userManager.addUser(newUser)) {
            try {
                //dang ky thanh cong -> chuyen den man hinh dang nhap
                Parent homeView = FXMLLoader.load(getClass().getResource("/com/example/demo/login-view.fxml"));
                Stage stage = (Stage) RegisteButton.getScene().getWindow();
                stage.setScene(new Scene(homeView));
                stage.setTitle("Login");
            } catch (IOException e) {
                //loi tai scene
                e.printStackTrace();
                System.err.println("Error loading Dictionary Application: " + e.getMessage());
            }
        } else {
            //dang ky that bai khi trung ten voi user khac
            WarningText.setText("Username are already taken, please try another name");
        }
    }

}
