module com.gestorcerveja.gestorcervejadesktop {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // IMPORTANT: allow JavaFX to access controller
    opens com.gestorcerveja.ui.controller to javafx.fxml;

    // Optional (but good practice)
    exports com.gestorcerveja.ui;
}