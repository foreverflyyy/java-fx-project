package com.example.javafxproject;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WeatherApp {
    private final WeatherService weatherService;
    private final VBox mainPane;
    private final Label resultLabel = new Label();;
    private final ImageView weatherIcon = new ImageView();

    public WeatherApp() {
        this.weatherService = new WeatherService();
        this.mainPane = createMainPane();
    }

    private VBox createMainPane() {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(20));

        Label cityLabel = new Label("Город:");
        TextField cityField = new TextField();

        HBox unitBox = createUnitToggleGroup();

        Button getWeatherButton = new Button("Узнать погоду");
        getWeatherButton.setOnAction(e -> {
            String city = cityField.getText();
            String unit = getSelectedUnit(unitBox);
            try {
                WeatherData weatherData = weatherService.getWeather(city, unit);
                updateWeatherInfo(weatherData);
            }
            catch(Exception a) {
                resultLabel.setText("Такой город был не найден!");
            }
        });

        pane.getChildren().addAll(cityLabel, cityField, unitBox, getWeatherButton, resultLabel, weatherIcon);
        return pane;
    }

    private HBox createUnitToggleGroup() {
        HBox unitBox = new HBox(10);

        ToggleGroup toggleGroup = new ToggleGroup();

        RadioButton celsiusButton = new RadioButton("°C");
        celsiusButton.setToggleGroup(toggleGroup);
        celsiusButton.setSelected(true);
        celsiusButton.setUserData("metric");

        RadioButton fahrenheitButton = new RadioButton("°F");
        fahrenheitButton.setToggleGroup(toggleGroup);
        fahrenheitButton.setUserData("imperial");

        unitBox.getChildren().addAll(new Label("Единицы измерения:"), celsiusButton, fahrenheitButton);

        return unitBox;
    }

    private String getSelectedUnit(HBox unitBox) {
        ToggleGroup toggleGroup = (ToggleGroup) ((RadioButton) unitBox.getChildren().get(1)).getToggleGroup();
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        return selectedRadioButton.getUserData().toString();
    }

    private void updateWeatherInfo(WeatherData weatherData) {
        resultLabel.setText("Текущая погода в " + weatherData.getCity() + ": " + weatherData.getTemperature());
        weatherIcon.setImage(new Image(weatherData.getIconUrl()));
    }

    public VBox getMainPane() {
        return mainPane;
    }
}