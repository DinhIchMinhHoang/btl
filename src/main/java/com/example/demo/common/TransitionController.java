package com.example.demo.common;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public abstract class TransitionController extends BaseController {

    protected void fadeInTransition(Node node) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        // Reset node state
        node.setOpacity(0.0);

        fadeTransition.play();
    }

    protected void slideInTransition(Node node, boolean fromRight) {
        // Initial setup
        node.setOpacity(0);
        double startX = fromRight ? 50 : -50;
        node.setTranslateX(startX);

        // Create transitions
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), node);
        translateTransition.setFromX(startX);
        translateTransition.setToX(0);

        // Combine transitions
        ParallelTransition parallelTransition = new ParallelTransition(
                fadeTransition,
                translateTransition
        );

        parallelTransition.play();
    }

    @Override
    protected void navigateToView(String fxmlPath, String title) {
        try {
            // Get current view
            Node currentView = HomeButton.getScene().getRoot();

            // Create fade out transition
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), currentView);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            fadeOut.setOnFinished(event -> {
                super.navigateToView(fxmlPath, title);

                // Get new view and apply fade in
                Node newView = HomeButton.getScene().getRoot();
                fadeInTransition(newView);
            });

            fadeOut.play();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error during transition to " + title + ": " + e.getMessage());
        }
    }
}

