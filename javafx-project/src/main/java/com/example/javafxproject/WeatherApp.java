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
    private final Label resultLabel = new Label();
    private final ImageView weatherIcon = new ImageView();
    private final ComboBox<String> timeOfDayComboBox;
    private ToggleGroup unitToggleGroup;

    public WeatherApp() {
        this.weatherService = new WeatherService();
        this.timeOfDayComboBox = createTimeOfDayComboBox();
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
            String unit = getSelectedUnit();
            String timeOfDay = timeOfDayComboBox.getValue();
            try {
                WeatherData weatherData = weatherService.getWeather(city, unit);
                updateWeatherInfo(weatherData, timeOfDay);
            } catch (Exception a) {
                resultLabel.setText("Такой город не был найден!");
            }
        });

        pane.getChildren().addAll(cityLabel, cityField, unitBox, timeOfDayComboBox, getWeatherButton, resultLabel, weatherIcon);
        return pane;
    }

    private ComboBox<String> createTimeOfDayComboBox() {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Утро", "День", "Вечер", "Ночь");
        comboBox.setValue("День");
        return comboBox;
    }

    private HBox createUnitToggleGroup() {
        HBox unitBox = new HBox(10);

        unitToggleGroup = new ToggleGroup();

        ToggleButton celsiusButton = new ToggleButton("°C");
        celsiusButton.setToggleGroup(unitToggleGroup);
        celsiusButton.setSelected(true);
        celsiusButton.setUserData("metric");

        ToggleButton fahrenheitButton = new ToggleButton("°F");
        fahrenheitButton.setToggleGroup(unitToggleGroup);
        fahrenheitButton.setUserData("imperial");

        unitBox.getChildren().addAll(new Label("Единицы измерения:"), celsiusButton, fahrenheitButton);
        return unitBox;
    }

    private String getSelectedUnit() {
        ToggleButton selectedToggleButton = (ToggleButton) unitToggleGroup.getSelectedToggle();
        return selectedToggleButton.getUserData().toString();
    }

    private void updateWeatherInfo(WeatherData weatherData, String timeOfDay) {
        resultLabel.setText("Текущая погода в " + weatherData.getCity() +
                " (" + timeOfDay + "): " + weatherData.getTemperature());
        weatherIcon.setImage(new Image(weatherData.getIconUrl()));

        String backImageUrl = weatherData.getBackgroundUrl();
        mainPane.setStyle("-fx-background-image: url('" + backImageUrl + "'); -fx-background-size: cover;");
    }

    public VBox getMainPane() {
        return mainPane;
    }
}