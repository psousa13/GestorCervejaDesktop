package com.gestorcerveja.ui;
import com.gestorcerveja.ui.screens.LoginScreen;
import com.gestorcerveja.ui.screens.MainScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;
public class JavaFXApplication extends Application {
    public static Stage primaryStage;
    @Override public void start(Stage stage){primaryStage=stage;stage.setTitle("Gestor Cerveja");stage.setMinWidth(1100);stage.setMinHeight(680);stage.setWidth(1280);stage.setHeight(760);showLogin();stage.show();}
    @Override public void stop(){var ctx=com.gestorcerveja.SpringContext.getContext();if(ctx instanceof ConfigurableApplicationContext c)c.close();}
    public static void showLogin(){primaryStage.setScene(new Scene(LoginScreen.build()));}
    public static void showMain(){primaryStage.setScene(new Scene(MainScreen.build()));}
}
