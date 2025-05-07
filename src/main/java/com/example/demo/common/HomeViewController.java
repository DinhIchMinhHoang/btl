package com.example.demo.common;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.Node;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * controller dieu khien giao dien trang chu (home-view.fxml)
 * quan ly, dieu khien hanh dong trong scene home-view
 * ke thua tu transitioncontroller
 */
public class HomeViewController extends TransitionController {

    @FXML
    public JFXButton HomeButton;

    /**
     * phuong thuc khoi tao
     * duoc goi tu dong khi file fxml duoc tai
     * hieu ung transition co tu lop cha
     * @param location vi tri duoc su dung de giai quyet duong dan tuong doi
     * @param resources tai nguyen duoc su dung de dia phuong hoa
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //goi phuong thuc initialize tu lop cha (TransitionController)
        super.initialize(location, resources);
        //lay node chinh cua giao dien
        Node mainContent = HomeButton.getParent().getParent();
        //ap dung hieu ung fade in
        fadeInTransition(mainContent);
    }


    /**
     * cai nay de chuyen vao scene home-view nhung vi user da o trong scene ne khong co gi trong nay
     */
    @FXML
    protected void onHomeButtonClick() {
    }

}