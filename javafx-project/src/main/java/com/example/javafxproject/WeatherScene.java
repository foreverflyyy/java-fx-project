package com.example.javafxproject;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherScene extends Scene {
    private final WeatherService weatherService;
    private WeatherData weatherData;
    private final VBox mainPane;
    private final Label resultLabel = new Label();
    private final ImageView weatherIcon = new ImageView();
    private final ImageView sun = new ImageView();
    private final ComboBox<String> timeOfDayComboBox;
    private ToggleGroup unitToggleGroup;
    private final List<WeatherData> history = new ArrayList<>();
    private LineChart<Number, Number> temperatureChart;
    private final ComboBox<String> timeIntervalComboBox;
    private final Button saveHistoryButton;
    private final Button logRequestButton;
    private static final String LOG_FILE_PATH = "weather_log.txt";
    private final HelloApplication helloApplication;

    public WeatherScene(HelloApplication helloApplication) {
        super(new VBox(), 1000, 800);
        this.helloApplication = helloApplication;

        this.weatherService = new WeatherService();
        this.timeOfDayComboBox = createTimeOfDayComboBox();
        this.mainPane = (VBox) getRoot();

        mainPane.getChildren().add(createMainPane());

        initTempGraphic();
        timeIntervalComboBox = createTimeIntervalComboBox();
        mainPane.getChildren().add(timeIntervalComboBox);

        Button goToTextInfoButton = createButton("Перейти к заполнению формы пользователя", this::goToTextInfoScene);
        goToTextInfoButton.setStyle("-fx-start-margin: 10");
        mainPane.getChildren().add(goToTextInfoButton);

        saveHistoryButton = createButton("Сохранить историю", this::saveHistoryToFile);
        logRequestButton = createButton("Логировать запрос", () -> logRequest(weatherData.getCity(), weatherData.getTemperature()));

        mainPane.getChildren().addAll(saveHistoryButton, logRequestButton);
    }

    private VBox createMainPane() {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(20));

        Label cityLabel = new Label("Город:");
        TextField cityField = new TextField();
        cityField.getStyleClass().add("text-field");

        HBox unitBox = createUnitToggleGroup();

        Button getWeatherButton = new Button("Узнать погоду");
        getWeatherButton.setOnAction(e -> {
            String city = cityField.getText();
            String unit = getSelectedUnit();
            String timeOfDay = timeOfDayComboBox.getValue();
            try {
                weatherData = weatherService.getWeather(city, unit);
                updateWeatherInfo(timeOfDay);
            } catch (Exception a) {
                resultLabel.setText("Такой город не был найден!");
            }
        });
        sun.getStyleClass().add("right-align");

        pane.getChildren().addAll(cityLabel, cityField, unitBox, timeOfDayComboBox, getWeatherButton, resultLabel, weatherIcon, sun);
        return pane;
    }

    private void initTempGraphic() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        temperatureChart = new LineChart<>(xAxis, yAxis);
        temperatureChart.setTitle("График температуры");
        mainPane.getChildren().add(temperatureChart);
    }

    private void goToTextInfoScene() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("textFieldScene.fxml"));
        TextInfoController controller = new TextInfoController(helloApplication);
        loader.setController(controller);
        try {
            Scene textInfoScene = new Scene(loader.load(), 1000, 800);
            textInfoScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            helloApplication.setScene(textInfoScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setOnAction(e -> action.run());
        return button;
    }

    private ComboBox<String> createTimeOfDayComboBox() {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Утро", "День", "Вечер", "Ночь");
        comboBox.setValue("День");
        return comboBox;
    }

    private ComboBox<String> createTimeIntervalComboBox() {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Все время", "Последний час", "Последние 24 часа", "Последние 7 дней");
        comboBox.setValue("Все время");
        comboBox.setOnAction(e -> displayTemperatureChart());
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

    private void updateWeatherInfo(String timeOfDay) {
        history.add(weatherData);

        resultLabel.setText("Текущая погода в " + weatherData.getCity() +
                " (" + timeOfDay + "): " + weatherData.getTemperature());
        weatherIcon.setImage(new Image(weatherData.getIconUrl()));
        sun.setImage(new Image(weatherData.getSunUrl()));


        String backImageUrl = weatherData.getBackgroundUrl();
        mainPane.setStyle("-fx-background-image: url('" + backImageUrl + "'); -fx-background-size: cover;");

        displayTemperatureChart();
    }

    private void displayTemperatureChart() {
        String selectedInterval = timeIntervalComboBox.getValue();

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Температура");

        for (int i = 0; i < history.size(); i++) {
            weatherData = history.get(i);
            double temperature = parseTemperature(weatherData.getTemperature());
            series.getData().add(new XYChart.Data<>(i + 1, temperature));
        }

        temperatureChart.getData().clear();
        temperatureChart.getData().add(series);
    }

    public void clearHistory() {
        history.clear();
        temperatureChart.getData().clear();
    }

    private double parseTemperature(String temperatureString) {
        return Double.parseDouble(temperatureString.replaceAll("[^\\d.]", ""));
    }

    public void saveHistoryToFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить историю в файл");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Текстовые файлы (*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (WeatherData weatherData : history) {
                    writer.write(weatherData.toString());
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void logRequest(String city, String unit) {
        boolean isCity = city != null && !city.trim().isEmpty();
        boolean isUnit = unit != null && !unit.trim().isEmpty();
        if (!isCity || !isUnit) {
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.write(timestamp + " - Запрос погоды для города: " + city + ", температура: " + unit);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public VBox getMainPane() {
        return mainPane;
    }

    public Tab getWeatherTab() {
        Tab tab = new Tab("Погода");
        tab.setContent(mainPane);
        return tab;
    }

    public Scene createScene() {
        return new WeatherScene(helloApplication);
    }
}