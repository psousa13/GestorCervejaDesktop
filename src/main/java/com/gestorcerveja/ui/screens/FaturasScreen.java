package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.components.*;
import com.gestorcerveja.ui.StyleConstants;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class FaturasScreen {

    public static ScreenBundle build() {
        TableView<String[]> table = TableBuilder.buildTable(
                new String[]{"Nº Fatura", "Pedido", "Total", "Emissão", "Estado"},
                new String[0][]);

        Runnable refresh = () -> {
            try {
                var pedidos = MainScreen.pedidoController.listAll();
                var rows = new ArrayList<String[]>();
                for (var p : pedidos) {
                    try {
                        var f = MainScreen.faturaController.getByPedido(p.getId());
                        rows.add(new String[]{
                                "FAT-" + String.format("%03d", f.getId()),
                                "#PED-" + String.format("%03d", p.getId()),
                                String.format("€ %.2f", f.getValorTotal()),
                                f.getDataEmissao().toString(),
                                f.getEstado()
                        });
                    } catch (Exception ignored) {}
                }
                table.setItems(FXCollections.observableArrayList(rows));
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

        // Faturas sao geradas a partir de pedidos — sem botao "+ Novo"
        return ScreenBundle.exportOnly(root, table);
    }
}
