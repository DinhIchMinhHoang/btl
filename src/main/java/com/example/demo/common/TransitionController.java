package com.example.demo.common;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Class truu tuong (lop con cua basecontroller)
 * cac medthod co chuc nang tao hieu ung chuyen tiep (transition) giua cac scene
 */

public abstract class TransitionController extends BaseController {

    /**
     * tao hieu ung mo dan cho giao dien
     * thanh phan se xuat hien tu trong suot den hien ro rang (thay doi opacity)
     * @param node thanh phan can ap dung hieu ung
     */
    protected void fadeInTransition(Node node) {
        //hieu ung co thoi luong 500ms
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), node);
        fadeTransition.setFromValue(0.0); //bat dau tu trong suot hoan toan
        fadeTransition.setToValue(1.0); //ket thuc o trang thai ro rang nhat

        //Dat lai trang thai ban dau cua thanh phan
        node.setOpacity(0.0);

        //bat dau hieu ung
        fadeTransition.play();
    }

    /**
     * tao hieu ung truot ket hop ma dan cho giao dien
     * thanh phan se truot tu trai sang hoac nguoc lai ket hop voi hieu ung mo dan
     * @param node thanh phan can ap dung hieu ung
     * @param fromRight true neu muon truot tu phai sang trai, false neu muon truot tu trai sang phai
     */
    protected void slideInTransition(Node node, boolean fromRight) {
        // Initial setup
        node.setOpacity(0); //trong suot hoan toan
        double startX = fromRight ? 50 : -50; // vi tri ban dau tuy thuoc vao huong truot
        node.setTranslateX(startX);

        // hieu ung mo dan (nhu medthod ben tren)
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        //hieu ung di chuyen
        //thoi luong 500ms
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), node);
        translateTransition.setFromX(startX); //vi tri bat dau
        translateTransition.setToX(0);//vi tri ket thuc

        // ket hop 2 hieu ung
        ParallelTransition parallelTransition = new ParallelTransition(
                fadeTransition,
                translateTransition
        );

        //bat dau hieu ung sau khi ket hop
        parallelTransition.play();
    }

    /**
     * hieu ung chuyen tiep giua cac scene voi nhau
     * @param fxmlPath duong dan den file fxml can toi
     * @param title tieu de window
     */
    @Override
    protected void navigateToView(String fxmlPath, String title) {
        try {
            // Scene hien tai
            Node currentView = HomeButton.getScene().getRoot();

            // hieu ung mo dan khi tat scene hien tai
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), currentView);
            fadeOut.setFromValue(1.0); //bat dau khi opacity = 1 (ro rang nhat)
            fadeOut.setToValue(0.0);// ket thuc khi opacity = 0 (hoan toan trong suot)

            //chuyen scene
            fadeOut.setOnFinished(event -> {
                //goi phuong thuc dieu huong cua lop cha (baseController)
                super.navigateToView(fxmlPath, title);
                //lay scene moi va ap dung medthod mo dan vao
                Node newView = HomeButton.getScene().getRoot();
                fadeInTransition(newView); // hieu ung mo dan vao
            });

            //hieu ung mo dan ra
            fadeOut.play();
        } catch (Exception e) {
            //bao loi khi chuyen scene neu co loi
            e.printStackTrace();
            System.err.println("Error during transition to " + title + ": " + e.getMessage());
        }
    }
}

