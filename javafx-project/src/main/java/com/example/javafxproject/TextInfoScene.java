package com.example.javafxproject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class TextInfoScene extends Scene {
    private final HelloApplication helloApplication;

    public TextInfoScene(String fxmlPath, HelloApplication helloApplication) {
        super(new VBox(), 1000, 800);
        this.helloApplication = helloApplication;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        fxmlLoader.setController(new TextInfoController(helloApplication));

        try {
            Parent root = fxmlLoader.load();
            setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Scene createScene(HelloApplication helloApplication) {
        return new TextInfoScene("/com/example/javafxproject/textFieldScene.fxml", helloApplication);
    }
}
