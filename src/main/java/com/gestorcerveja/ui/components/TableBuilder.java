package com.gestorcerveja.ui.components;

import com.gestorcerveja.ui.StyleConstants;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class TableBuilder {

    public static VBox build(String[] headers, String[][] rows) {
        VBox root = new VBox(0);
        root.setPadding(new Insets(18, 22, 18, 22));
        root.setStyle(StyleConstants.CONTENT_BG);

        TableView<String[]> table = new TableView<>();
        table.setStyle(StyleConstants.TABLE_VIEW);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox.setVgrow(table, Priority.ALWAYS);

        for (int i = 0; i < headers.length; i++) {
            final int col = i;
            TableColumn<String[], String> column = new TableColumn<>(headers[i]);
            column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[col]));
            table.getColumns().add(column);
        }

        if (rows != null && rows.length > 0) {
            table.setItems(FXCollections.observableArrayList(rows));
        }

        root.getChildren().add(table);
        return root;
    }

    public static Label errorLabel(String msg) {
        Label l = new Label(msg);
        l.setStyle("-fx-text-fill: #A32D2D; -fx-font-size: 12px; -fx-padding: 10px;");
        return l;
    }
}