package com.example.javafxproject;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TextInfoScene extends Scene {

    private final VBox mainPane;
    private final TextArea textArea;

    public TextInfoScene() {
        super(new VBox(), 800, 600);

        this.mainPane = (VBox) getRoot();
        this.textArea = createTextArea();

        mainPane.getChildren().addAll(createMainPane(), textArea);
    }

    private VBox createMainPane() {
        VBox pane = new VBox(10);
        pane.setPadding(new Insets(20));

        Label titleLabel = new Label("Текстовая информация");
        titleLabel.getStyleClass().add("title-label");

        Button goToWeatherButton = createButton("Вернуться к WeatherScene", () -> goToWeatherScene());

        pane.getChildren().addAll(titleLabel, goToWeatherButton);
        return pane;
    }

    private TextArea createTextArea() {
        TextArea area = new TextArea();
        area.setPromptText("Введите текстовую информацию здесь...");
        area.setPrefRowCount(10);
        area.setPrefColumnCount(40);
        return area;
    }

    private void goToWeatherScene() {
        WeatherScene weatherScene = new WeatherScene();
        weatherScene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setScene(weatherScene);
    }

    private Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setOnAction(e -> action.run());
        return button;
    }

    public VBox getMainPane() {
        return mainPane;
    }

    public Tab getTextInfoTab() {
        Tab tab = new Tab("Текстовая информация");
        tab.setContent(mainPane);
        return tab;
    }

    public static Scene createScene() {
        return new TextInfoScene();
    }
}