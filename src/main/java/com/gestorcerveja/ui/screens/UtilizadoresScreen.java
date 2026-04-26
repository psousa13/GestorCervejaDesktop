package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.StyleConstants;
import com.gestorcerveja.ui.components.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.List;

public class UtilizadoresScreen {

    public static ScreenBundle build() {
        TableView<String[]> table = TableBuilder.buildTable(
                new String[]{"ID", "Nome", "Role"}, new String[0][]);

        Runnable refresh = () -> {
            try {
                table.setItems(FXCollections.observableArrayList(
                    MainScreen.usuarioController.listAll().stream().map(u -> new String[]{
                        String.valueOf(u.getId()), u.getNome(), String.valueOf(u.getIdrole())
                    }).toList()));
            } catch (SQLException e) { table.setPlaceholder(TableBuilder.errorLabel("Erro: " + e.getMessage())); }
        };
        refresh.run();

        table.getColumns().add(TableActions.column(
            row -> openEditModal(row, refresh),
            row -> { try { MainScreen.usuarioController.delete(Integer.parseInt(row[0])); refresh.run(); }
                     catch (Exception e) { throw new RuntimeException(e); } }
        ));

        VBox root = new VBox(0);
        root.setPadding(new Insets(18, 22, 18, 22));
        root.setStyle(StyleConstants.CONTENT_BG);
        VBox.setVgrow(table, Priority.ALWAYS);
        root.getChildren().add(table);

        List<FormField> roleOptions = getRoleOptions();
        List<FormField> newFields = List.of(
            new FormField("nome",   "Nome de utilizador", FormField.Type.TEXT),
            new FormField("senha",  "Senha",              FormField.Type.PASSWORD),
            new FormField("idrole", "Role",               roleOptions.get(0).getType() == FormField.Type.COMBO
                    ? FormField.Type.COMBO : FormField.Type.NUMBER,
                    roleOptions.isEmpty() ? List.of() : roleOptions.get(0).getOptions())
        );

        return new ScreenBundle(root, table, () -> new ModalOverlay(
            MainScreen.contentArea, "Novo Utilizador", newFields, values -> {
                String nome  = values.get("nome");
                String senha = values.get("senha");
                if (nome.isBlank())  throw new IllegalArgumentException("Nome é obrigatório.");
                if (senha.isBlank()) throw new IllegalArgumentException("Senha é obrigatória.");
                String roleRaw = values.get("idrole");
                int idrole = parseInt(roleRaw.contains("–") ? roleRaw.split("–")[0].trim() : roleRaw, 1);
                MainScreen.usuarioController.create(nome, senha, idrole);
                refresh.run();
            }).show());
    }

    private static void openEditModal(String[] row, Runnable refresh) {
        int id = parseInt(row[0], 0);
        List<FormField> fields = List.of(
            new FormField("nome",         "Nome",       FormField.Type.TEXT, List.of(), row[1]),
            new FormField("novaSenha",    "Nova senha", FormField.Type.PASSWORD, List.of(), "Deixar vazio = não alterar"),
            new FormField("confirmarSenha","Confirmar", FormField.Type.PASSWORD)
        );
        new ModalOverlay(MainScreen.contentArea, "Editar Utilizador #" + id, fields, values -> {
            String novaSenha = values.get("novaSenha");
            String confirmar = values.get("confirmarSenha");
            if (!novaSenha.isBlank() && !novaSenha.equals(confirmar))
                throw new IllegalArgumentException("As senhas não coincidem.");
            MainScreen.usuarioController.updateSelf(id, values.get("nome"), novaSenha);
            refresh.run();
        }).show();
    }

    private static List<FormField> getRoleOptions() {
        try {
            var roles = MainScreen.roleController.listAll();
            List<String> opts = roles.stream().map(r -> r.getId() + " – " + r.getNome()).toList();
            return List.of(new FormField("idrole","Role", FormField.Type.COMBO, opts));
        } catch (SQLException e) {
            return List.of(new FormField("idrole","ID Role", FormField.Type.NUMBER));
        }
    }

    private static int parseInt(String s, int fallback) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return fallback; }
    }
}
