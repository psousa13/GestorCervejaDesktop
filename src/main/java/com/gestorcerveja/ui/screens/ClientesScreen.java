package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.components.TableBuilder;
import javafx.scene.layout.VBox;
import java.sql.SQLException;

public class ClientesScreen {
    public static VBox build() {
        try {
            var clientes = MainScreen.clienteService.getAll();
            String[][] rows = clientes.stream().map(c -> new String[]{
                    String.valueOf(c.getId()),
                    c.getTipoCliente(),
                    c.getEmail() != null ? c.getEmail() : "—",
                    c.getTelefone() != null ? c.getTelefone() : "—",
                    c.getDataRegisto().toString(),
            }).toArray(String[][]::new);
            return TableBuilder.build(
                    new String[]{"ID","Tipo","Email","Telefone","Registo"},
                    rows
            );
        } catch (SQLException e) {
            return new VBox(TableBuilder.errorLabel("Erro ao carregar clientes: " + e.getMessage()));
        }
    }
}