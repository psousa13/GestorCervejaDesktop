package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.StyleConstants;
import com.gestorcerveja.ui.components.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.List;

public class IngredientesScreen {

    public static ScreenBundle build() {
        TableView<String[]> table = TableBuilder.buildTable(
                new String[]{"ID", "Nome", "Unidade", "Stock Atual", "Stock Mín.", "Estado"},
                new String[0][]);

        Runnable refresh = () -> {
            try {
                table.setItems(FXCollections.observableArrayList(
                    MainScreen.ingredienteController.listAll().stream().map(i -> new String[]{
                        String.valueOf(i.getId()), i.getNome(),
                        i.getUnidade() != null ? i.getUnidade() : "—",
                        String.valueOf(i.getStockAtual()), String.valueOf(i.getStockMinimo()),
                        i.getStockAtual() < i.getStockMinimo() ? "Crítico" : "OK"
                    }).toList()));
            } catch (SQLException e) { table.setPlaceholder(TableBuilder.errorLabel("Erro: " + e.getMessage())); }
        };
        refresh.run();

        // Coluna de ações
        table.getColumns().add(TableActions.column(
            row -> openEditModal(row, refresh),
            row -> { try { MainScreen.ingredienteController.delete(Integer.parseInt(row[0])); refresh.run(); }
                     catch (Exception e) { throw new RuntimeException(e); } }
        ));

        VBox root = buildRoot(table);

        List<FormField> fields = List.of(
            new FormField("nome",     "Nome",          FormField.Type.TEXT,  List.of(), "ex: Lúpulo Cascade"),
            new FormField("unidade",  "Unidade",       FormField.Type.COMBO, List.of("kg","g","L","ml","unidade")),
            new FormField("stockAtual",  "Stock Atual",  FormField.Type.NUMBER, List.of(), "ex: 50"),
            new FormField("stockMinimo", "Stock Mínimo", FormField.Type.NUMBER, List.of(), "ex: 10")
        );

        return new ScreenBundle(root, table, () -> new ModalOverlay(
            MainScreen.contentArea, "Novo Ingrediente", fields, values -> {
                String nome = values.get("nome");
                if (nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
                MainScreen.ingredienteController.create(nome, values.get("unidade"),
                    parseDouble(values.get("stockAtual")), parseDouble(values.get("stockMinimo")));
                refresh.run();
            }).show());
    }

    private static void openEditModal(String[] row, Runnable refresh) {
        int id = Integer.parseInt(row[0]);
        List<FormField> fields = List.of(
            new FormField("nome",       "Nome",          FormField.Type.TEXT,  List.of(), row[1]),
            new FormField("unidade",    "Unidade",       FormField.Type.COMBO, List.of("kg","g","L","ml","unidade")),
            new FormField("stockAtual",  "Stock Atual",  FormField.Type.NUMBER, List.of(), row[3]),
            new FormField("stockMinimo", "Stock Mínimo", FormField.Type.NUMBER, List.of(), row[4])
        );
        new ModalOverlay(MainScreen.contentArea, "Editar Ingrediente", fields, values -> {
            MainScreen.ingredienteController.update(id, values.get("nome"), values.get("unidade"),
                parseDouble(values.get("stockAtual")), parseDouble(values.get("stockMinimo")));
            refresh.run();
        }).show();
    }

    private static VBox buildRoot(TableView<String[]> table) {
        VBox root = new VBox(0);
        root.setPadding(new Insets(18, 22, 18, 22));
        root.setStyle(StyleConstants.CONTENT_BG);
        VBox.setVgrow(table, Priority.ALWAYS);
        root.getChildren().add(table);
        return root;
    }

    private static double parseDouble(String s) {
        try { return Double.parseDouble(s.replace(',', '.')); } catch (NumberFormatException e) { return 0; }
    }
}
