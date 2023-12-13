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
        WeatherScene weatherScene = new WeatherScene(this);
        weatherScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(weatherScene);
    }

    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}