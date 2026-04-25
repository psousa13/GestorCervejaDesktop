package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.components.*;
import com.gestorcerveja.ui.StyleConstants;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;

import java.sql.SQLException;

public class ProducaoScreen {

    public static ScreenBundle build() {
        TableView<String[]> table = TableBuilder.buildTable(
                new String[]{"Request", "Utilizador", "Estado", "Criado em", "Conclusão"},
                new String[0][]);

        Runnable refresh = () -> {
            try {
                var list = MainScreen.requestController.listAll();
                table.setItems(FXCollections.observableArrayList(
                        list.stream().map(r -> new String[]{
                                "#REQ-" + String.format("%03d", r.getId()),
                                String.valueOf(r.getIdusuario()),
                                r.getEstado(),
                                r.getDataCriacao().toString(),
                                r.getDataConclusao() != null ? r.getDataConclusao().toString() : "—"
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

        // Produção gerida internamente — apenas exportação disponível
        return ScreenBundle.exportOnly(root, table);
    }
}
