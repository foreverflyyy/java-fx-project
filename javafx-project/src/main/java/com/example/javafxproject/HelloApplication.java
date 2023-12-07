package com.example.javafxproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        WeatherApp weatherApp = new WeatherApp();
        Scene scene = new Scene(weatherApp.getMainPane(), 800, 600);

        stage.setTitle("Онлайн-погода");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}