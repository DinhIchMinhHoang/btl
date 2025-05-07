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

/**
 * controller cho login-view.fxml
 */
public class LoginController {

    @FXML
    public JFXButton LoginButton, SignUpButton;

    public JFXTextField UserName;

    public JFXPasswordField Password;

    public Label Wrong;

    private UserManager userManager;

    /**
     * khoi tao controller, thiet lap cac listener cho cac textfield
     */
    public void initialize() {

        userManager = new UserManager();//khoi tao quan ly nguoi dung

        LoginButton.setDisable(true);//disable login button

        //them listener cho textfield username
        UserName.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputs();
        });

        //them listener cho passwordfield password
        Password.textProperty().addListener((observable, oldValue, newValue) -> {
            validateInputs();
        });
    }

    /**
     * xac thuc du lieu nhap vao
     * kiem tra xem da co du lieu nhap vao chua
     */
    protected void validateInputs() {
        //kiem tra xem textarea co du lieu khong
        boolean isValid = !UserName.getText().trim().isEmpty() &&
                !Password.getText().trim().isEmpty();

        LoginButton.setDisable(!isValid);//enable/disable button
    }


    /**
     * chuyen scene sang man hinh dang ky
     */
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

    /**
     * xac thuc thong tin dang nhap
     */
    @FXML
    protected void onLoginButtonClick() {
        //lay thong tin dang nhap tu cac textarea
        String username = UserName.getText().trim();
        String password = Password.getText().trim();

        //kiem data tren console
        System.out.println("Attempting login with:");
        System.out.println("Username: '" + username + "'");
        System.out.println("Password: '" + password + "'");

        //xac thuc thong tin dang nhap
        //neu trung khop
        if (userManager.verifyUser(username, password)) {
            try {
                //dang nhap thanh cong -> chuyen den scene trang chu
                Parent homeView = FXMLLoader.load(getClass().getResource("/com/example/demo/home-view.fxml"));
                Stage stage = (Stage) LoginButton.getScene().getWindow();
                stage.setScene(new Scene(homeView));
                stage.setTitle("Dictionary Application");
                //loi tai scene
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error loading Dictionary Application: " + e.getMessage());
            }
        } else {
            //dang nhap that bai, hien thi thong bao
            Wrong.setText("Invalid Username or Password");
        }
    }


}

