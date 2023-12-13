package com.example.javafxproject;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextInfoController {

    @FXML
    private VBox mainPane;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private WeatherScene weatherScene;

    public void setWeatherScene(WeatherScene weatherScene) {
        this.weatherScene = weatherScene;
    }

    private final HelloApplication helloApplication;

    public TextInfoController(HelloApplication helloApplication) {
        this.helloApplication = helloApplication;
    }

    @FXML
    private void goToWeatherScene() {
        if (helloApplication != null) {
            WeatherScene weatherScene = new WeatherScene(helloApplication);
            weatherScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            helloApplication.setScene(weatherScene);
        } else {
            System.err.println("HelloApplication is null. Cannot switch to WeatherScene.");
        }
    }

    @FXML
    private void register() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String textInfo = textArea.getText();

        if (!username.isEmpty() && !password.isEmpty() && !textInfo.isEmpty()) {
            // Здесь можно добавить логику записи в файл
            writeToFile(username, password, textInfo);
            clearFields();
            showAlert("Регистрация успешна!");
        } else {
            showAlert("Заполните все поля!");
        }
    }

    private void writeToFile(String username, String password, String textInfo) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить в файл");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Текстовые файлы (*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("Username: " + username);
                writer.newLine();
                writer.write("Password: " + password);
                writer.newLine();
                writer.write("Text Info: " + textInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        textArea.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Результат регистрации");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}