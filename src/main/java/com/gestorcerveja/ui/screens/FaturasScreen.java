package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.components.TableBuilder;
import javafx.scene.layout.VBox;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FaturasScreen {
    public static VBox build() {
        try {
            var pedidos = MainScreen.pedidoService.getAll();
            List<String[]> rows = new ArrayList<>();
            for (var p : pedidos) {
                try {
                    var f = MainScreen.faturaService.getByPedido(p.getId());
                    rows.add(new String[]{
                            "FAT-" + String.format("%03d", f.getId()),
                            "#PED-" + String.format("%03d", p.getId()),
                            String.format("€ %.2f", f.getValorTotal()),
                            f.getDataEmissao().toString(),
                            f.getEstado(),
                    });
                } catch (Exception ignored) {}
            }
            return TableBuilder.build(
                    new String[]{"Nº Fatura","Pedido","Total","Emissão","Estado"},
                    rows.toArray(new String[0][])
            );
        } catch (SQLException e) {
            return new VBox(TableBuilder.errorLabel("Erro ao carregar faturas: " + e.getMessage()));
        }
    }
}