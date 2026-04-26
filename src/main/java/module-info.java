module com.gestorcerveja.gestorcervejadesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires transitive javafx.graphics;

    opens com.gestorcerveja.ui to javafx.fxml;
    exports com.gestorcerveja.ui;
    exports com.gestorcerveja.model;
    exports com.gestorcerveja.ui.components;
    exports com.gestorcerveja.ui.util;
}
