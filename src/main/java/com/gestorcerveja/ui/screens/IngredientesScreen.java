package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.components.*;
import com.gestorcerveja.ui.StyleConstants;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.List;

public class IngredientesScreen {

    public static ScreenBundle build() {
        // ── tabela ──────────────────────────────────────────────────────────
        TableView<String[]> table = TableBuilder.buildTable(
                new String[]{"Nome", "Unidade", "Stock Atual", "Stock Mínimo", "Estado"},
                new String[0][]);

        Runnable refresh = () -> {
            try {
                var list = MainScreen.ingredienteController.listAll();
                table.setItems(FXCollections.observableArrayList(
                        list.stream().map(i -> new String[]{
                                i.getNome(),
                                i.getUnidade() != null ? i.getUnidade() : "—",
                                String.valueOf(i.getStockAtual()),
                                String.valueOf(i.getStockMinimo()),
                                i.getStockAtual() < i.getStockMinimo() ? "Crítico" : "OK"
                        }).toList()));
            } catch (SQLException e) {
                table.setPlaceholder(TableBuilder.errorLabel("Erro: " + e.getMessage()));
            }
        };
        refresh.run();

        // ── layout ──────────────────────────────────────────────────────────
        VBox root = new VBox(0);
        root.setPadding(new Insets(18, 22, 18, 22));
        root.setStyle(StyleConstants.CONTENT_BG);
        VBox.setVgrow(table, Priority.ALWAYS);
        root.getChildren().add(table);

        // ── formulario novo ─────────────────────────────────────────────────
        List<FormField> fields = List.of(
                new FormField("nome",     "Nome",         FormField.Type.TEXT,   List.of(), "ex: Lúpulo Cascade"),
                new FormField("unidade",  "Unidade",      FormField.Type.COMBO,
                        List.of("kg", "g", "L", "ml", "unidade")),
                new FormField("stockAtual",  "Stock Atual",   FormField.Type.NUMBER, List.of(), "ex: 50"),
                new FormField("stockMinimo", "Stock Mínimo",  FormField.Type.NUMBER, List.of(), "ex: 10")
        );

        Runnable onNew = () -> new ModalOverlay(
                MainScreen.contentArea,
                "Novo Ingrediente",
                fields,
                values -> {
                    String nome   = values.get("nome");
                    String unid   = values.get("unidade");
                    double atual  = parseDouble(values.get("stockAtual"), 0);
                    double minimo = parseDouble(values.get("stockMinimo"), 0);
                    if (nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
                    MainScreen.ingredienteController.create(nome, unid, atual, minimo);
                    refresh.run();
                }
        ).show();

        return new ScreenBundle(root, table, onNew);
    }

    private static double parseDouble(String s, double fallback) {
        try { return Double.parseDouble(s.replace(',', '.')); }
        catch (NumberFormatException e) { return fallback; }
    }
}
