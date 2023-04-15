package com.pressing.pressingplus;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class PresentationController implements Initializable{

    public ImageView logo;
    public VBox cnt;
    public Button btn;
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(3000));
        scaleTransition.setNode(logo);
        scaleTransition.setByY(1.5);
        scaleTransition.setByX(1.5);
        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(false);
        scaleTransition.play();

        if (new Timestamp(System.currentTimeMillis()).getTime() - timestamp.getTime() > 3000) {

        }
        System.out.println(new Timestamp(System.currentTimeMillis()).getTime() - timestamp.getTime());

        logo.onMouseClickedProperty();
    }
}
