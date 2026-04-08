package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.components.TableBuilder;
import javafx.scene.layout.VBox;
import java.sql.SQLException;

public class PedidosScreen {
    public static VBox build() {
        try {
            var pedidos = MainScreen.pedidoService.getAll();
            String[][] rows = pedidos.stream().map(p -> new String[]{
                    "#PED-" + String.format("%03d", p.getId()),
                    String.valueOf(p.getIdcliente()),
                    p.getDataPedido().toString(),
                    p.getEstado(),
                    p.getDataEstimadaConclusao() != null ? p.getDataEstimadaConclusao().toString() : "—",
            }).toArray(String[][]::new);
            return TableBuilder.build(
                    new String[]{"Nº Pedido","Cliente","Data","Estado","Estimativa"},
                    rows
            );
        } catch (SQLException e) {
            VBox root = new VBox(TableBuilder.errorLabel("Erro ao carregar pedidos: " + e.getMessage()));
            return root;
        }
    }
}