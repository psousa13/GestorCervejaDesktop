module com.gestorcerveja.gestorcervejadesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens com.gestorcerveja.ui to javafx.fxml;
    exports com.gestorcerveja.ui;
}