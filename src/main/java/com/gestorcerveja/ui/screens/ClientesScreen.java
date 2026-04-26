package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.StyleConstants;
import com.gestorcerveja.ui.components.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.List;

public class ClientesScreen {

    public static ScreenBundle build() {
        TableView<String[]> table = TableBuilder.buildTable(
                new String[]{"ID", "Tipo", "Email", "Telefone", "Registo"}, new String[0][]);

        Runnable refresh = () -> {
            try {
                table.setItems(FXCollections.observableArrayList(
                    MainScreen.clienteController.listAll().stream().map(c -> new String[]{
                        String.valueOf(c.getId()), c.getTipoCliente(),
                        c.getEmail()    != null ? c.getEmail()    : "—",
                        c.getTelefone() != null ? c.getTelefone() : "—",
                        c.getDataRegisto().toString()
                    }).toList()));
            } catch (SQLException e) { table.setPlaceholder(TableBuilder.errorLabel("Erro: " + e.getMessage())); }
        };
        refresh.run();

        table.getColumns().add(TableActions.column(
            row -> openEditModal(row, refresh),
            row -> { try { MainScreen.clienteController.delete(Integer.parseInt(row[0])); refresh.run(); }
                     catch (Exception e) { throw new RuntimeException(e); } }
        ));

        VBox root = new VBox(0);
        root.setPadding(new Insets(18, 22, 18, 22));
        root.setStyle(StyleConstants.CONTENT_BG);
        VBox.setVgrow(table, Priority.ALWAYS);
        root.getChildren().add(table);

        List<FormField> fields = List.of(
            new FormField("nomeCompleto", "Nome Completo", FormField.Type.TEXT,   List.of(), "ex: João Silva"),
            new FormField("nif",          "NIF",           FormField.Type.TEXT,   List.of(), "ex: 123456789"),
            new FormField("email",        "Email",         FormField.Type.TEXT,   List.of(), "ex: joao@email.com"),
            new FormField("telefone",     "Telefone",      FormField.Type.TEXT,   List.of(), "ex: 912345678")
        );

        return new ScreenBundle(root, table, () -> new ModalOverlay(
            MainScreen.contentArea, "Novo Cliente Particular", fields, values -> {
                String nome = values.get("nomeCompleto");
                if (nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
                MainScreen.clienteController.createParticular(
                    values.get("email"), values.get("telefone"), nome, values.get("nif"));
                refresh.run();
            }).show());
    }

    private static void openEditModal(String[] row, Runnable refresh) {
        int id = Integer.parseInt(row[0]);
        // Tenta carregar dados completos do particular; cai para dados base se for revendedor
        String nomeAtual = "—", nifAtual = "";
        try {
            var cp = MainScreen.clienteController.getParticular(id);
            nomeAtual = cp.getNomeCompleto();
            nifAtual  = cp.getNif() != null ? cp.getNif() : "";
        } catch (Exception ignored) {}

        final String nomeF = nomeAtual, nifF = nifAtual;
        List<FormField> fields = List.of(
            new FormField("nomeCompleto", "Nome Completo", FormField.Type.TEXT, List.of(), nomeF),
            new FormField("nif",          "NIF",           FormField.Type.TEXT, List.of(), nifF),
            new FormField("email",        "Email",         FormField.Type.TEXT, List.of(), row[2].equals("—") ? "" : row[2]),
            new FormField("telefone",     "Telefone",      FormField.Type.TEXT, List.of(), row[3].equals("—") ? "" : row[3])
        );
        new ModalOverlay(MainScreen.contentArea, "Editar Cliente #" + id, fields, values -> {
            MainScreen.clienteController.updateParticular(id,
                values.get("email"), values.get("telefone"),
                values.get("nomeCompleto"), values.get("nif"));
            refresh.run();
        }).show();
    }
}
