package com.example.javafxproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(root);

//        scene.getStylesheets().add("styles.css");
        stage.getIcons().add(new Image("file:image.png"));
//        scene.getStylesheets().add("styles.css");
//        Image icon = new Image("/image.png");
//        stage.getIcons().add(icon);
//        stage.setResizable(false);

        stage.setWidth(400);
        stage.setHeight(400);
        stage.setTitle("Hello World!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}