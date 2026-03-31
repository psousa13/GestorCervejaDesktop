package com.gestorcerveja.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        try {
            primaryStage = stage;

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/gestorcerveja/gestorcervejadesktop/login.fxml")
            );

            Scene scene = new Scene(loader.load(), 400, 300);

            stage.setTitle("Gestor Cerveja");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}