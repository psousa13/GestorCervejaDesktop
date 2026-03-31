package com.gestorcerveja.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        stage.setTitle("Gestor Cerveja");
        stage.setMinWidth(1100);
        stage.setMinHeight(680);
        stage.setWidth(1280);
        stage.setHeight(760);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}