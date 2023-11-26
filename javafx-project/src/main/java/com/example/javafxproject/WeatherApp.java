package com.example.javafxproject;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class WeatherApp {
    private final WeatherService weatherService;
    private final VBox mainPane;

    public WeatherApp() {
        this.weatherService = new WeatherService();
        this.mainPane = createMainPane();
    }

    private VBox createMainPane() {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(20));

        Label cityLabel = new Label("Город:");
        TextField cityField = new TextField();

        Label resultLabel = new Label();

        Button getWeatherButton = new Button("Узнать погоду");
        getWeatherButton.setOnAction(e -> {
            String city = cityField.getText();
            String weather = weatherService.getWeather(city);
            resultLabel.setText("Текущая погода в " + city + ": " + weather);
        });

        pane.getChildren().addAll(cityLabel, cityField, getWeatherButton, resultLabel);
        return pane;
    }

    public VBox getMainPane() {
        return mainPane;
    }
}