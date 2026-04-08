package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.components.TableBuilder;
import javafx.scene.layout.VBox;
import java.sql.SQLException;

public class ProducaoScreen {
    public static VBox build() {
        try {
            var requests = MainScreen.requestService.getAll();
            String[][] rows = requests.stream().map(r -> new String[]{
                    "#REQ-" + String.format("%03d", r.getId()),
                    String.valueOf(r.getIdusuario()),
                    r.getEstado(),
                    r.getDataCriacao().toString(),
                    r.getDataConclusao() != null ? r.getDataConclusao().toString() : "—",
            }).toArray(String[][]::new);
            return TableBuilder.build(
                    new String[]{"Request","Utilizador","Estado","Criado em","Conclusão"},
                    rows
            );
        } catch (SQLException e) {
            return new VBox(TableBuilder.errorLabel("Erro ao carregar produção: " + e.getMessage()));
        }
    }
}