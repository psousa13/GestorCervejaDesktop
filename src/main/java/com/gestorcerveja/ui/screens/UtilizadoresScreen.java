package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.components.TableBuilder;
import javafx.scene.layout.VBox;
import java.sql.SQLException;

public class UtilizadoresScreen {
    public static VBox build() {
        try {
            var usuarios = MainScreen.usuarioService.getAll();
            String[][] rows = usuarios.stream().map(u -> new String[]{
                    String.valueOf(u.getId()),
                    u.getNome(),
                    String.valueOf(u.getIdrole()),
            }).toArray(String[][]::new);
            return TableBuilder.build(
                    new String[]{"ID","Nome","Role"},
                    rows
            );
        } catch (SQLException e) {
            return new VBox(TableBuilder.errorLabel("Erro ao carregar utilizadores: " + e.getMessage()));
        }
    }
}