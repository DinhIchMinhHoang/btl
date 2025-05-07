package com.example.demo.auth;

import com.example.demo.common.TransitionController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXPasswordField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * controller cho giao dien quan ly nguoi dung
 */
public class AccountController extends TransitionController {

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXPasswordField currentPasswordField;

    @FXML
    private JFXPasswordField newPasswordField;

    @FXML
    private JFXButton saveChangesButton;

    @FXML
    private JFXButton deleteAccountButton;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private Label warningLabel;

    private UserManager userManager;
    private User currentUser;

    /**
     * khoi tao controller va thiet lap giao dien
     * them transition
     * @param location vi tri duoc su dung de giai quyet duong dan tuong doi
     * @param resources tai nguyen duoc su dung de dia phuong hoa
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);//goi medthod tu lop cha

        //khoi tao quan ly nguoi dung va lay nguoi dung hien tai
        userManager = new UserManager();
        currentUser = userManager.getCurrentUser();

        //hien thi ten nguoi dung tren textarea
        if (currentUser != null) {
            usernameField.setPromptText(currentUser.getUsername());
        }

        //cai nay de cho transition
        usernameField.setOpacity(0);
        currentPasswordField.setOpacity(0);
        newPasswordField.setOpacity(0);
        saveChangesButton.setOpacity(0);
        logoutButton.setOpacity(0);
        deleteAccountButton.setOpacity(0);

        //transition
        javafx.application.Platform.runLater(() -> {
            javafx.animation.Timeline timeline = new javafx.animation.Timeline();
            //slide transition
            timeline.getKeyFrames().add(
                    new javafx.animation.KeyFrame(javafx.util.Duration.ZERO,
                            e -> slideInTransition(usernameField, false))
            );
            //slide transition
            timeline.getKeyFrames().add(
                    new javafx.animation.KeyFrame(javafx.util.Duration.millis(150),
                            e -> slideInTransition(currentPasswordField, false))
            );
            //slide transition
            timeline.getKeyFrames().add(
                    new javafx.animation.KeyFrame(javafx.util.Duration.millis(300),
                            e -> slideInTransition(newPasswordField, false))
            );
            //fade transition
            timeline.getKeyFrames().add(
                    new javafx.animation.KeyFrame(javafx.util.Duration.millis(450),
                            e -> fadeInTransition(saveChangesButton))
            );
            //fade transition
            timeline.getKeyFrames().add(
                    new javafx.animation.KeyFrame(javafx.util.Duration.millis(600),
                            e -> fadeInTransition(logoutButton))
            );
            //fade transition
            timeline.getKeyFrames().add(
                    new javafx.animation.KeyFrame(javafx.util.Duration.millis(750),
                            e -> fadeInTransition(deleteAccountButton))
            );
            //chay hieu ung
            timeline.play();
        });
        //thiet lap xu ly dieu kien cac button
        setupEventHandlers();
    }

    /**
     * thiet lap xu ly su kien cho cac button
     */
    private void setupEventHandlers() {
        //save button
        saveChangesButton.setOnAction(event -> handleSaveChanges());
        //delete account button
        deleteAccountButton.setOnAction(event -> handleDeleteAccount());
        //logout button
        logoutButton.setOnAction(event -> handleLogout());
    }

    private void handleSaveChanges() {
        //kiem tra xem co nguoi dung nao dang nhap nhap khong
        if (currentUser == null) {
            showWarning("No user logged in");//thong bao neu khong co (cai nay kho xay ra tru khi thay doi ma nguon)
            return;
        }

        //lap data tu cac textarea
        String newUsername = usernameField.getText().trim();
        String currentPassword = currentPasswordField.getText().trim();
        String newPassword = newPasswordField.getText().trim();

        //kiem tra mat khau hien tai co chinh xac khong
        if (!currentPassword.isEmpty() && !currentPassword.equals(currentUser.getPassword())) {
            showWarning("Current password is incorrect");
            return;
        }

        //thay doi ten nguoi dung
        if (!newUsername.isEmpty() && !newUsername.equals(currentUser.getUsername())) {
            //kiem tra xem ten nguoi dung moi co bi trung khop khong
            for (User user : userManager.getAllUsers()) {
                if (user.getUsername().equals(newUsername) && !user.getUsername().equals(currentUser.getUsername())) {
                    showWarning("Username already exists");//thong bao neu trung khop
                    return;
                }
            }
            //cap nhap ten nguoi dung moi
            currentUser.setUsername(newUsername);
        }

        //thay doi mat khau
        if (!newPassword.isEmpty()) {
            //kiem tra mat khau moi co khac so voi mk cu khong
            if (newPassword.equals(currentPassword)) {
                showWarning("New password must be different from current password");//thong bao neu khong khac
                return;
            }
            //update mat khau moi
            currentUser.setPassword(newPassword);
        }

        //luu thay doi vao file neu co thay doi
        if (!newUsername.isEmpty() || !newPassword.isEmpty()) {
            userManager.updateUserInFile(currentUser);
            showWarning("Changes saved successfully!", false);
            //reset textarea
            usernameField.clear();
            currentPasswordField.clear();
            newPasswordField.clear();
            //update ten nguoi dung
            usernameField.setPromptText(currentUser.getUsername());
        } else {
            showWarning("No changes to save"); // neu khong co thi tra ve thong bao
        }
    }

    private void handleDeleteAccount() {
        //kiem tra xem co nguoi dung dang nhap khong
        if (currentUser == null) {
            showWarning("No user logged in");
            return;
        }

        //xoa tai khoan, xu ly ket qua
        if (userManager.deleteUser(currentUser.getUsername())) {
            showWarning("Account deleted successfully", false);
            //sau khi xoa -> tu dong dang xuat
            handleLogout();
        } else {
            showWarning("Failed to delete account");
        }
    }

    private void handleLogout() {
        userManager.setCurrentUser(null);// dat nguoi dung dang nhap hien tai == null
        //chuyen scene
        navigateToView("/com/example/demo/login-view.fxml", "Login");
    }

    //hien thi thong bao
    private void showWarning(String message) {
        //thong bao mac dinh
        showWarning(message, true);
    }

    /**
     * hien thi thong bao theo mau
     * @param message noi dung
     * @param isError true neu error (red), false neu thanh cong (green)
     */
    private void showWarning(String message, boolean isError) {
        warningLabel.setText(message);//thiet lap thong bao
        //thiet lap mau thong bao
        warningLabel.setStyle(isError ? "-fx-text-fill: #ff0000;" : "-fx-text-fill: #00aa00;");
        warningLabel.setVisible(true);//hien thi thong bao
    }

    //chuyen scene ve trang chu
    @Override
    protected void onHomeButtonClick() {
        navigateToView("/com/example/demo/home-view.fxml", "Dictionary Application");
    }
}