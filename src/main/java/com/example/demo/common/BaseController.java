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


/**
 * class cha: quan ly chuc nang cho tat ca cac controller
 * dieu khien dieu huong cac scene
 * class nay giup tranh lap code va tien khi muon chinh sua code tranh gay loi khong dang co
 */
public abstract class BaseController implements Initializable {

    @FXML
    protected JFXButton TranslaterButton, HomeButton, GameButton, SearchButton, accountButton;

    @FXML
    protected JFXButton CloseButton, MinimizeButton;

    @FXML
    protected HBox headBar;

    //dat cac vitri khi bat dau keo (lam draggable underdecorated window)
    protected double xOffset = 0, yOffset = 0;

    /**\
     * khoi tao controller voi cac chuc nang co ban
     * chuc nang keo tha windows
     * @param location vi tri duoc su dung de giai quyet duong dan tuong doi
     * @param resources tai nguyen duoc su dung de dia phuong hoa
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Thiet lap chuc nang keo tha window
        setupWindowDragging();
    }

    /**
     * medthod keo tha window (topbar)
     * draggable underdecorated windows
     */
    private void setupWindowDragging() {
        headBar.setOnMousePressed(event -> {
            // luu vi tri khi nhan chuot
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        //di chuyen cua so theo chuot
        headBar.setOnMouseDragged(event -> {
            Stage stage = (Stage) headBar.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    /**
     * cai nay de thu nho window
     */
    @FXML
    protected void onMinimizeButtonClick() {
        //lay window hien tai va thu nho xuong
        Stage stage = (Stage) MinimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * cai nay de tat app
     */
    @FXML
    protected void onCloseButtonClick() {
        //lay window hien tai va tat app
        Stage stage = (Stage) CloseButton.getScene().getWindow();
        stage.close();
    }

    /**
     * medthod thuc hien dieu huong dien scene khac
     * tai va hien thi file fxml
     * @param fxmlPath
     * @param title
     */
    protected void navigateToView(String fxmlPath, String title) {
        try {
            // tai giao dien tu file fxml
            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
            // cap nhap window voi scene moi
            Stage stage = (Stage) HomeButton.getScene().getWindow();
            stage.setScene(new Scene(view));
            stage.setTitle(title);
        } catch (IOException e) {
            // bao loi khi tai scene
            e.printStackTrace();
            System.err.println("Error loading " + title + " view: " + e.getMessage());
        }
    }

    /**
     * chuyen dien scene dich
     */
    @FXML
    protected void onTranslaterButtonClick() {
        navigateToView("/com/example/demo/translate-view.fxml", "Translator");
    }

    /**
     * chuyen den trang chu
     */
    @FXML
    protected void onHomeButtonClick() {
        navigateToView("/com/example/demo/home-view.fxml", "Dictionary Application");
    }

    /**
     * chuyen den game
     */
    @FXML
    protected void onGameButtonClick() {
        navigateToView("/com/example/demo/game-view.fxml", "Hangman Game");
    }

    /**
     * chuyen den chuc nang tu dien
     */
    @FXML
    protected void onSearchButtonClick() {
        navigateToView("/com/example/demo/search-view.fxml", "Search");
    }

    /**
     * chuyen den chuc nang account editer
     */
    @FXML
    protected void onAccountButtonClick() {
        navigateToView("/com/example/demo/account-view.fxml", "Account");
    }
}
