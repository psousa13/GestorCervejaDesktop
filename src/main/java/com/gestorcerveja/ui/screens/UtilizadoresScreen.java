package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.components.*;
import com.gestorcerveja.ui.StyleConstants;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.List;

public class UtilizadoresScreen {

    public static ScreenBundle build() {
        TableView<String[]> table = TableBuilder.buildTable(
                new String[]{"ID", "Nome", "Role"},
                new String[0][]);

        Runnable refresh = () -> {
            try {
                var list = MainScreen.usuarioController.listAll();
                table.setItems(FXCollections.observableArrayList(
                        list.stream().map(u -> new String[]{
                                String.valueOf(u.getId()),
                                u.getNome(),
                                String.valueOf(u.getIdrole())
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

        List<FormField> fields;
        try {
            var roles = MainScreen.roleController.listAll();
            List<String> roleOptions = roles.stream()
                    .map(r -> r.getId() + " – " + r.getNome())
                    .toList();
            fields = List.of(
                    new FormField("nome",   "Nome de utilizador", FormField.Type.TEXT,     List.of(), "ex: operador1"),
                    new FormField("senha",  "Senha",             FormField.Type.PASSWORD),
                    new FormField("idrole", "Role",              FormField.Type.COMBO,     roleOptions)
            );
        } catch (SQLException e) {
            fields = List.of(
                    new FormField("nome",   "Nome",   FormField.Type.TEXT),
                    new FormField("senha",  "Senha",  FormField.Type.PASSWORD),
                    new FormField("idrole", "ID Role", FormField.Type.NUMBER, List.of(), "ex: 1")
            );
        }

        final List<FormField> finalFields = fields;

        Runnable onNew = () -> new ModalOverlay(
                MainScreen.contentArea,
                "Novo Utilizador",
                finalFields,
                values -> {
                    String nome  = values.get("nome");
                    String senha = values.get("senha");
                    if (nome.isBlank())  throw new IllegalArgumentException("Nome é obrigatório.");
                    if (senha.isBlank()) throw new IllegalArgumentException("Senha é obrigatória.");
                    // extrai o id do role (pode vir como "1 – admin" ou "1")
                    String roleRaw = values.get("idrole");
                    int idrole = parseInt(roleRaw.contains("–")
                            ? roleRaw.split("–")[0].trim() : roleRaw, 1);
                    MainScreen.usuarioController.create(nome, senha, idrole);
                    refresh.run();
                }
        ).show();

        return new ScreenBundle(root, table, onNew);
    }

    private static int parseInt(String s, int fallback) {
        try { return Integer.parseInt(s.trim()); } catch (NumberFormatException e) { return fallback; }
    }
}
