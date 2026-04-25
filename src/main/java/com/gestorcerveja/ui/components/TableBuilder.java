package com.gestorcerveja.ui.components;

import com.gestorcerveja.ui.StyleConstants;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class TableBuilder {

    /**
     * Constroi uma VBox com TableView<String[]> dentro.
     * Retorna um ScreenBundle com a view e a table (sem onNew).
     */
    public static ScreenBundle buildBundle(String[] headers, String[][] rows) {
        VBox root = new VBox(0);
        root.setPadding(new Insets(18, 22, 18, 22));
        root.setStyle(StyleConstants.CONTENT_BG);

        TableView<String[]> table = buildTable(headers, rows);
        VBox.setVgrow(table, Priority.ALWAYS);

        root.getChildren().add(table);
        return new ScreenBundle(root, table, null);
    }

    /**
     * Constroi apenas a TableView (util para screens que gerem o layout manualmente).
     */
    public static TableView<String[]> buildTable(String[] headers, String[][] rows) {
        TableView<String[]> table = new TableView<>();
        table.setStyle(StyleConstants.TABLE_VIEW);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        for (int i = 0; i < headers.length; i++) {
            final int col = i;
            TableColumn<String[], String> column = new TableColumn<>(headers[i]);
            column.setCellValueFactory(data -> new SimpleStringProperty(
                    col < data.getValue().length ? data.getValue()[col] : ""));
            table.getColumns().add(column);
        }

        if (rows != null && rows.length > 0)
            table.setItems(FXCollections.observableArrayList(rows));

        return table;
    }

    /** Atualiza os items de uma TableView existente. */
    public static void refresh(TableView<String[]> table, String[][] rows) {
        if (rows == null || rows.length == 0)
            table.getItems().clear();
        else
            table.setItems(FXCollections.observableArrayList(rows));
    }

    /** Label de erro estilizado (inalterado). */
    public static Label errorLabel(String msg) {
        Label l = new Label(msg);
        l.setStyle("-fx-text-fill: #A32D2D; -fx-font-size: 12px; -fx-padding: 10px;");
        return l;
    }

    // Compatibilidade retroactiva ─────────────────────────────────────────────
    /** @deprecated use buildBundle() */
    @Deprecated
    public static VBox build(String[] headers, String[][] rows) {
        return buildBundle(headers, rows).view();
    }
}
