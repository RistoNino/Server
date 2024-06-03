package org.uid.ristonino.server.controller;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;



public class NotificationController {
    @FXML
    public Label textNotification;

    @FXML
    public Label iconNotification;

    @FXML
    public HBox paneNotification;


    static NotificationController instance;
    public static NotificationController getInstance() {
        if (instance == null) {
            instance = new NotificationController();
        }
        return instance;
    }


    public FadeTransition doAnimationIn() {

        FadeTransition fadeInRemove = new FadeTransition(Duration.millis(250), paneNotification);
        fadeInRemove.setFromValue(0);
        fadeInRemove.setToValue(1);
        fadeInRemove.play();

        return fadeInRemove;
    }

    public FadeTransition doAnimationOut() {

        FadeTransition fadeOutRemove = new FadeTransition(Duration.millis(1500), paneNotification);
        fadeOutRemove.setFromValue(1.0);
        fadeOutRemove.setToValue(0);

        fadeOutRemove.play();

        return fadeOutRemove;
    }




    public Node setNotification(String text, String icon){
        textNotification.setText(text);
        FontIcon labelIcon = new FontIcon(icon);
        labelIcon.setIconSize(30);
        labelIcon.setFill(Color.GREEN);
        iconNotification.setGraphic(labelIcon);

        return paneNotification;
    }
}
