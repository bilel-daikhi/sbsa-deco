package org.sbsa.deco.utils;

import javafx.animation.ScaleTransition;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class Transition {
    public Transition() {

    }

    public static void onMouseEntered(MouseEvent event) {
        ImageView image = (ImageView) event.getSource();
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), image);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.play();
    }

    public static void OnMOuseExited(MouseEvent event) {
        ImageView image = (ImageView) event.getSource();
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), image);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
    }

    public static void imageOnMouseEntered(MouseEvent event) {
        ImageView image = (ImageView) event.getSource();
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), image);
        scaleTransition.setToX(0.8);
        scaleTransition.setToY(0.8);
        scaleTransition.play();
    }

}
