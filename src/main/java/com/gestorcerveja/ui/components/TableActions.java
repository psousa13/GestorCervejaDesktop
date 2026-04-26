package com.gestorcerveja.ui.components;

import com.gestorcerveja.ui.StyleConstants;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.function.Consumer;

/**
 * Coluna de ações (Editar / Apagar) para qualquer {@link TableView<String[]>}.
 *
 * <pre>
 *   table.getColumns().add(TableActions.column(
 *       row -> openEditModal(row),
 *       row -> { controller.delete(Integer.parseInt(row[0])); refresh.run(); }
 *   ));
 * </pre>
 */
public class TableActions {

    public static TableColumn<String[], Void> column(
            Consumer<String[]> onEdit,
            Consumer<String[]> onDelete) {

        TableColumn<String[], Void> col = new TableColumn<>("Ações");
        col.setSortable(false);
        col.setResizable(false);
        col.setPrefWidth(130);

        col.setCellFactory(tc -> new TableCell<>() {
            private final Button btnEdit   = makeBtn("✏ Editar",  StyleConstants.BTN_SECONDARY);
            private final Button btnDelete = makeBtn("✕ Apagar",  deleteStyle());
            private final HBox   box       = new HBox(6, btnEdit, btnDelete);

            {
                box.setAlignment(Pos.CENTER);
                btnEdit.setOnAction(e -> {
                    String[] row = getTableView().getItems().get(getIndex());
                    onEdit.accept(row);
                });
                btnDelete.setOnAction(e -> {
                    String[] row = getTableView().getItems().get(getIndex());
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Confirmar eliminação");
                    a.setHeaderText(null);
                    a.setContentText("Tem a certeza que deseja apagar este registo?");
                    a.showAndWait().ifPresent(btn -> {
                        if (btn == ButtonType.OK) onDelete.accept(row);
                    });
                });
            }

            @Override
            protected void updateItem(Void v, boolean empty) {
                super.updateItem(v, empty);
                setGraphic(empty ? null : box);
            }
        });

        return col;
    }

    private static Button makeBtn(String text, String style) {
        Button b = new Button(text);
        b.setStyle(style);
        return b;
    }

    private static String deleteStyle() {
        return "-fx-background-color: white;" +
               "-fx-text-fill: #A32D2D;" +
               "-fx-border-color: #F5CECE; -fx-border-width: 1px; -fx-border-radius: 7px;" +
               "-fx-background-radius: 7px; -fx-font-size: 11px;" +
               "-fx-padding: 4px 8px; -fx-cursor: hand;";
    }
}
