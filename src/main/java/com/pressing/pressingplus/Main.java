package com.pressing.pressingplus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene_presentation.fxml")));
        Scene scene = new Scene(root, 1000, 900);
        stage.setTitle("pressingPlus");
        stage.getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("logo4_14_125853.png"))));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}