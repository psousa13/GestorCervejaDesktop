package com.gestorcerveja.ui;

import com.gestorcerveja.ui.screens.LoginScreen;
import com.gestorcerveja.ui.screens.MainScreen;
import javafx.application.Application;
import javafx.scene.Scene;
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
        showLogin();
        stage.show();
    }

    public static void showLogin() {
        Scene scene = new Scene(LoginScreen.build());
        primaryStage.setScene(scene);
    }

    public static void showMain() {
        Scene scene = new Scene(MainScreen.build());
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}