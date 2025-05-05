package com.example.demo.common;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.Node;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeViewController extends TransitionController {

    @FXML
    public JFXButton HomeButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        Node mainContent = HomeButton.getParent().getParent();
        fadeInTransition(mainContent);
    }


    @FXML
    protected void onHomeButtonClick() {
    }

}