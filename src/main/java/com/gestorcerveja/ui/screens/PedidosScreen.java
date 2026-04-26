package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.StyleConstants;
import com.gestorcerveja.ui.components.*;
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
                new String[]{"ID", "Nº Pedido", "Cliente", "Data", "Estado", "Estimativa"}, new String[0][]);

        Runnable refresh = () -> {
            try {
                table.setItems(FXCollections.observableArrayList(
                    MainScreen.pedidoController.listAll().stream().map(p -> new String[]{
                        String.valueOf(p.getId()),
                        "#PED-" + String.format("%03d", p.getId()),
                        String.valueOf(p.getIdcliente()),
                        p.getDataPedido().toString(),
                        p.getEstado(),
                        p.getDataEstimadaConclusao() != null ? p.getDataEstimadaConclusao().toString() : "—"
                    }).toList()));
            } catch (SQLException e) { table.setPlaceholder(TableBuilder.errorLabel("Erro: " + e.getMessage())); }
        };
        refresh.run();

        table.getColumns().add(TableActions.column(
            row -> openEditModal(row, refresh),
            row -> { try { MainScreen.pedidoController.delete(Integer.parseInt(row[0])); refresh.run(); }
                     catch (Exception e) { throw new RuntimeException(e); } }
        ));

        // Esconde a coluna ID (index 0) — só usada internamente
        table.getColumns().get(0).setVisible(false);

        VBox root = new VBox(0);
        root.setPadding(new Insets(18, 22, 18, 22));
        root.setStyle(StyleConstants.CONTENT_BG);
        VBox.setVgrow(table, Priority.ALWAYS);
        root.getChildren().add(table);

        List<FormField> fields = List.of(
            new FormField("idcliente",    "ID Cliente",          FormField.Type.NUMBER, List.of(), "ex: 3"),
            new FormField("dataEstimada", "Data Est. Conclusão", FormField.Type.DATE)
        );

        return new ScreenBundle(root, table, () -> new ModalOverlay(
            MainScreen.contentArea, "Novo Pedido", fields, values -> {
                int idc = parseInt(values.get("idcliente"));
                LocalDate data = values.get("dataEstimada").isBlank()
                    ? LocalDate.now().plusDays(7)
                    : LocalDate.parse(values.get("dataEstimada"));
                MainScreen.pedidoController.create(idc, data);
                refresh.run();
            }).show());
    }

    private static void openEditModal(String[] row, Runnable refresh) {
        int id = parseInt(row[0]);
        List<FormField> fields = List.of(
            new FormField("estado", "Estado", FormField.Type.COMBO,
                List.of("Pendente","Em Produção","Concluído","Cancelado")),
            new FormField("dataEstimada", "Data Est. Conclusão", FormField.Type.DATE)
        );
        new ModalOverlay(MainScreen.contentArea, "Editar Pedido #PED-" + String.format("%03d", id), fields, values -> {
            MainScreen.pedidoController.updateEstado(id, values.get("estado"));
            refresh.run();
        }).show();
    }

    private static int parseInt(String s) {
        try { return Integer.parseInt(s.trim()); } catch (NumberFormatException e) { return 0; }
    }
}
