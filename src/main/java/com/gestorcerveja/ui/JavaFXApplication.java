package com.gestorcerveja.ui;

import com.gestorcerveja.ui.screens.LoginScreen;
import com.gestorcerveja.ui.screens.MainScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Ciclo de vida JavaFX.
 *
 * Separado do {@code GestorCervejaApplication} para que o Spring seja
 * inicializado ANTES do JavaFX — garantindo que todos os beans estão
 * disponíveis quando os screens os pedem via {@link com.gestorcerveja.SpringContext}.
 */
public class JavaFXApplication extends Application {

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

    @Override
    public void stop() {
        // Fecha o contexto Spring ao fechar a janela
        var ctx = com.gestorcerveja.SpringContext.getContext();
        if (ctx instanceof ConfigurableApplicationContext cac) cac.close();
    }

    public static void showLogin() {
        primaryStage.setScene(new Scene(LoginScreen.build()));
    }

    public static void showMain() {
        primaryStage.setScene(new Scene(MainScreen.build()));
    }
}
