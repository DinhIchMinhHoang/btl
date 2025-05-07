package com.example.demo.translate;

import com.example.demo.common.TransitionController;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class nay lam controller cho giao dien dich trong app (dieu khien translate-view.fxml)
 * Xu ly chuc nang dich va hoat anh (UI)
 * dich van ban Anh-Viet (mac dinh: Tieng Anh sang Viet)
 */
public class TranslateController extends TransitionController {
    private String source = "en", target = "vi"; //ma ngon ngu nguon va ngon ngu dich

    @FXML

    public JFXButton SwapButton, TranslateButton;

    public TextArea TranslateTarget, TranslateExplain;

    public Label EnglishLabel, VietnameseLabel;


    /**
     * khoi tao controller voi hoat anh transition lay tu class TransitionController
     * medthod nay duoc goi khi file fxml duoc tai
     * @param location vi tri duoc su dung de giai quyet duong dan tuong doi cho doi tuong goc
     * @param resources tai nguyen duoc su dung de dia phuong hoa doi tuong goc
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);// goi phuong thuc initialize tu lop cha (TransitionController)

        // lay van ban dich
        Node sourceText = TranslateTarget;
        Node targetText = TranslateExplain;

        // cai nay de transition sao cho muot
        slideInTransition(sourceText, false);
        slideInTransition(targetText, true);

        // cai nay de lam transition cho cac button
        Node swapButton = SwapButton;
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), swapButton);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setDelay(Duration.millis(300));

        swapButton.setOpacity(0.0);
        fadeIn.play();
    }

    /**
     * Phuong thuc khoi tao phu
     * Disable button dich khi chua co van ban nao duoc nhap vao
     */
    @FXML
    public void initialize() {
        TranslateButton.setDisable(true);//vo hieu hoa nut dich tu ban dau (Translate Button)
        //them Listener thay doi van ban (cai nay de enable hoac disable translate button)
        TranslateTarget.textProperty().addListener((observable, oldValue, newValue) -> {
            TranslateButton.setDisable(newValue.trim().isEmpty());
        });
    }

    /**
     * noi chung la cai nay khi bam dich thi no se dich
     */
    @FXML
    protected void onTranslateButtonClick() {
        //Tao doi tuong API dich
        TranslateAPI translateAPI = new TranslateAPI();
        //Lay van ban can dich
        String texttoTranslate = TranslateTarget.getText();
        //thuc hien viec dich
        String translatedText = translateAPI.translateText(texttoTranslate, source, target);
        //Hien thi ket qua
        TranslateExplain.setText(translatedText);
    }


    /**
     * cai nay swap giu 2 ngon ngu voi nhau (default la Anh sang Viet thi button nay doi thanh Viet sang Anh)
     */
    @FXML
    protected void onSwapButtonClick() {
        //swap ngon ngu
        String tempLang = source;
        source = target;
        target = tempLang;

        //swap van ban
        String EnglishText = EnglishLabel.getText();
        String VietnameseText = VietnameseLabel.getText();
        EnglishLabel.setText(VietnameseText);
        VietnameseLabel.setText(EnglishText);

        //reset van ban dich
        TranslateTarget.clear();
        TranslateExplain.clear();
    }

    /**
     * cai nay la xu ly nut navigate den translate view
     * nhung vi user da o trong translate view nen medthod nay khong co gi
     */
    @FXML
    protected void onTranslaterButtonClick() {
    }

}