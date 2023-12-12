package com.example.javafxproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        showWeatherScene();

        this.stage.setTitle("Онлайн-погода");
        this.stage.show();
    }

    private void showWeatherScene() {
        Scene scene = WeatherScene.createScene();
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
    }

    private void showTextInfoScene() {
        Scene scene = TextInfoScene.createScene();
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}