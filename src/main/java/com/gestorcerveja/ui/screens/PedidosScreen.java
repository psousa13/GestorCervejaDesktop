package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.components.*;
import com.gestorcerveja.ui.StyleConstants;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PedidosScreen {

    public static ScreenBundle build() {
        TableView<String[]> table = TableBuilder.buildTable(
                new String[]{"Nº Pedido", "Cliente", "Data", "Estado", "Estimativa"},
                new String[0][]);

        Runnable refresh = () -> {
            try {
                var list = MainScreen.pedidoController.listAll();
                table.setItems(FXCollections.observableArrayList(
                        list.stream().map(p -> new String[]{
                                "#PED-" + String.format("%03d", p.getId()),
                                String.valueOf(p.getIdcliente()),
                                p.getDataPedido().toString(),
                                p.getEstado(),
                                p.getDataEstimadaConclusao() != null
                                        ? p.getDataEstimadaConclusao().toString() : "—"
                        }).toList()));
            } catch (SQLException e) {
                table.setPlaceholder(TableBuilder.errorLabel("Erro: " + e.getMessage()));
            }
        };
        refresh.run();

        VBox root = new VBox(0);
        root.setPadding(new Insets(18, 22, 18, 22));
        root.setStyle(StyleConstants.CONTENT_BG);
        VBox.setVgrow(table, Priority.ALWAYS);
        root.getChildren().add(table);

        List<FormField> fields = List.of(
                new FormField("idcliente",     "ID Cliente",       FormField.Type.NUMBER, List.of(), "ex: 3"),
                new FormField("dataEstimada",  "Data Est. Conclusão", FormField.Type.DATE)
        );

        Runnable onNew = () -> new ModalOverlay(
                MainScreen.contentArea,
                "Novo Pedido",
                fields,
                values -> {
                    int idc = parseInt(values.get("idcliente"), 0);
                    LocalDate data = values.get("dataEstimada").isBlank()
                            ? LocalDate.now().plusDays(7)
                            : LocalDate.parse(values.get("dataEstimada"));
                    MainScreen.pedidoController.create(idc, data);
                    refresh.run();
                }
        ).show();

        return new ScreenBundle(root, table, onNew);
    }

    private static int parseInt(String s, int fallback) {
        try { return Integer.parseInt(s.trim()); } catch (NumberFormatException e) { return fallback; }
    }
}
