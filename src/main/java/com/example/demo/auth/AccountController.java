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

    @FXML
    private VBox accountFormContainer;

    private UserManager userManager;
    private User currentUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        userManager = new UserManager();
        currentUser = userManager.getCurrentUser();

        if (currentUser != null) {
            usernameField.setPromptText(currentUser.getUsername());
        }
        fadeInTransition(accountFormContainer);
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        saveChangesButton.setOnAction(event -> handleSaveChanges());
        deleteAccountButton.setOnAction(event -> handleDeleteAccount());
        logoutButton.setOnAction(event -> handleLogout());
    }

    private void handleSaveChanges() {
        if (currentUser == null) {
            showWarning("No user logged in");
            return;
        }

        String newUsername = usernameField.getText().trim();
        String currentPassword = currentPasswordField.getText().trim();
        String newPassword = newPasswordField.getText().trim();

        if (!currentPassword.isEmpty() && !currentPassword.equals(currentUser.getPassword())) {
            showWarning("Current password is incorrect");
            return;
        }

        if (!newUsername.isEmpty() && !newUsername.equals(currentUser.getUsername())) {
            for (User user : userManager.getAllUsers()) {
                if (user.getUsername().equals(newUsername) && !user.getUsername().equals(currentUser.getUsername())) {
                    showWarning("Username already exists");
                    return;
                }
            }
            currentUser.setUsername(newUsername);
        }

        if (!newPassword.isEmpty()) {
            if (newPassword.equals(currentPassword)) {
                showWarning("New password must be different from current password");
                return;
            }
            currentUser.setPassword(newPassword);
        }

        if (!newUsername.isEmpty() || !newPassword.isEmpty()) {
            userManager.updateUserInFile(currentUser);
            showWarning("Changes saved successfully!", false);
            usernameField.clear();
            currentPasswordField.clear();
            newPasswordField.clear();
            usernameField.setPromptText(currentUser.getUsername());
        } else {
            showWarning("No changes to save");
        }
    }

    private void handleDeleteAccount() {
        if (currentUser == null) {
            showWarning("No user logged in");
            return;
        }

        if (userManager.deleteUser(currentUser.getUsername())) {
            showWarning("Account deleted successfully", false);
            handleLogout();
        } else {
            showWarning("Failed to delete account");
        }
    }

    private void handleLogout() {
        userManager.setCurrentUser(null);
        navigateToView("/com/example/demo/login-view.fxml", "Login");
    }

    private void showWarning(String message) {
        showWarning(message, true);
    }

    private void showWarning(String message, boolean isError) {
        warningLabel.setText(message);
        warningLabel.setStyle(isError ? "-fx-text-fill: #ff0000;" : "-fx-text-fill: #00aa00;");
        warningLabel.setVisible(true);
    }

    @Override
    protected void onHomeButtonClick() {
        navigateToView("/com/example/demo/home-view.fxml", "Dictionary Application");
    }
}