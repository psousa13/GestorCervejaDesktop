package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.SessionManager;
import com.gestorcerveja.ui.StyleConstants;
import com.gestorcerveja.ui.components.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.List;

public class ProducaoScreen {

    public static ScreenBundle build() {
        TableView<String[]> table = TableBuilder.buildTable(
                new String[]{"ID", "Request", "Utilizador", "Estado", "Criado em", "Conclusão"}, new String[0][]);

        Runnable refresh = () -> {
            try {
                table.setItems(FXCollections.observableArrayList(
                    MainScreen.requestController.listAll().stream().map(r -> new String[]{
                        String.valueOf(r.getId()),
                        "#REQ-" + String.format("%03d", r.getId()),
                        String.valueOf(r.getIdusuario()),
                        r.getEstado(),
                        r.getDataCriacao().toString(),
                        r.getDataConclusao() != null ? r.getDataConclusao().toString() : "—"
                    }).toList()));
            } catch (SQLException e) { table.setPlaceholder(TableBuilder.errorLabel("Erro: " + e.getMessage())); }
        };
        refresh.run();

        // Editar = marcar como Concluído; Apagar = delete
        table.getColumns().add(TableActions.column(
            row -> {
                int id = Integer.parseInt(row[0]);
                List<FormField> fields = List.of(
                    new FormField("estado", "Estado", FormField.Type.COMBO,
                        List.of("Pendente", "Em Progresso", "Concluido", "Cancelado"))
                );
                new ModalOverlay(MainScreen.contentArea, "Editar Request #REQ-" + String.format("%03d", id), fields, values -> {
                    String estado = values.get("estado");
                    if ("Concluido".equals(estado))
                        MainScreen.requestController.concluir(id);
                    // Se precisar de suportar outros estados, adicionar método no controller
                    refresh.run();
                }).show();
            },
            row -> { /* Requests de produção não são apagados diretamente — mostrar aviso */
                throw new UnsupportedOperationException("Requests de produção não podem ser apagados diretamente.");
            }
        ));
        table.getColumns().get(0).setVisible(false);

        VBox root = new VBox(0);
        root.setPadding(new Insets(18, 22, 18, 22));
        root.setStyle(StyleConstants.CONTENT_BG);
        VBox.setVgrow(table, Priority.ALWAYS);
        root.getChildren().add(table);

        // Obter utilizador da sessão
        var user = SessionManager.getUser();
        int idUsuarioAtual = user != null ? user.getId() : 1;

        List<FormField> newFields = List.of(
            new FormField("idusuario", "ID Utilizador", FormField.Type.NUMBER,
                List.of(), String.valueOf(idUsuarioAtual))
        );

        Runnable onNew = () -> new ModalOverlay(
            MainScreen.contentArea, "Nova Etapa de Produção", newFields, values -> {
                int idU = parseInt(values.get("idusuario"), idUsuarioAtual);
                MainScreen.requestController.create(idU);
                refresh.run();
            }).show();

        return new ScreenBundle(root, table, onNew);
    }

    private static int parseInt(String s, int fallback) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return fallback; }
    }
}
