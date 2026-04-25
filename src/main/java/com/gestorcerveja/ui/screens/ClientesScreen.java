package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.components.*;
import com.gestorcerveja.ui.StyleConstants;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.List;

public class ClientesScreen {

    public static ScreenBundle build() {
        TableView<String[]> table = TableBuilder.buildTable(
                new String[]{"ID", "Tipo", "Email", "Telefone", "Registo"},
                new String[0][]);

        Runnable refresh = () -> {
            try {
                var list = MainScreen.clienteController.listAll();
                table.setItems(FXCollections.observableArrayList(
                        list.stream().map(c -> new String[]{
                                String.valueOf(c.getId()),
                                c.getTipoCliente(),
                                c.getEmail()    != null ? c.getEmail()    : "—",
                                c.getTelefone() != null ? c.getTelefone() : "—",
                                c.getDataRegisto().toString()
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

        // Formulario para cliente Particular (tipo mais comum)
        List<FormField> fields = List.of(
                new FormField("nomeCompleto", "Nome Completo", FormField.Type.TEXT,   List.of(), "ex: João Silva"),
                new FormField("nif",          "NIF",          FormField.Type.TEXT,   List.of(), "ex: 123456789"),
                new FormField("email",        "Email",        FormField.Type.TEXT,   List.of(), "ex: joao@email.com"),
                new FormField("telefone",     "Telefone",     FormField.Type.TEXT,   List.of(), "ex: 912345678")
        );

        Runnable onNew = () -> new ModalOverlay(
                MainScreen.contentArea,
                "Novo Cliente Particular",
                fields,
                values -> {
                    String nome = values.get("nomeCompleto");
                    if (nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
                    MainScreen.clienteController.createParticular(
                            values.get("email"),
                            values.get("telefone"),
                            nome,
                            values.get("nif"));
                    refresh.run();
                }
        ).show();

        return new ScreenBundle(root, table, onNew);
    }
}
