package lk.ijse.hrms.util;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.util.Duration;

public class Animation {
    public static void scalaTransaction(Node node) {
        //logo
        ScaleTransition scale = new ScaleTransition();
        scale.setNode(node);
        scale.setDuration(Duration.millis(1000));
        scale.setCycleCount(TranslateTransition.INDEFINITE);
        scale.setInterpolator(Interpolator.LINEAR);
        scale.setByX(0.08);
        scale.setByY(0.08);
        scale.setAutoReverse(true);
        scale.play();
    }

    public static void translateTransition(Node node) {
        //text fields
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(node);
        transition.setDuration(Duration.millis(100));
        transition.setCycleCount(6);
        transition.setByX(5);
        transition.setAutoReverse(true);
        transition.play();

    }

    public static void translateTransitionMore(Node node) {
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(node);
        transition.setDuration(Duration.millis(1000));
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.setByX(10);
        transition.setAutoReverse(true);
        transition.play();

    }
    public static void scalaTransactionText(Node node) {
        //logo
        ScaleTransition scale = new ScaleTransition();
        scale.setNode(node);
        scale.setDuration(Duration.millis(1000));
        scale.setCycleCount(TranslateTransition.INDEFINITE);
        scale.setInterpolator(Interpolator.LINEAR);
        scale.setByX(0.010);
        scale.setByY(0.010);
        scale.setAutoReverse(true);
        scale.play();
    }
}
