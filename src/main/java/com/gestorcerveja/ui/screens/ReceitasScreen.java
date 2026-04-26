package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.StyleConstants;
import com.gestorcerveja.ui.components.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.List;

public class ReceitasScreen {

    public static ScreenBundle build() {
        TableView<String[]> table = TableBuilder.buildTable(
                new String[]{"ID", "Nome", "Descrição", "Preço/L"}, new String[0][]);

        Runnable refresh = () -> {
            try {
                table.setItems(FXCollections.observableArrayList(
                    MainScreen.receitaController.listAll().stream().map(r -> {
                        String preco;
                        try { preco = String.format("€ %.2f/L", MainScreen.receitaController.getActivePrice(r.getId())); }
                        catch (Exception ex) { preco = "Sem preço"; }
                        return new String[]{ String.valueOf(r.getId()), r.getNome(),
                            r.getDescricao() != null ? r.getDescricao() : "—", preco };
                    }).toList()));
            } catch (SQLException e) { table.setPlaceholder(TableBuilder.errorLabel("Erro: " + e.getMessage())); }
        };
        refresh.run();

        table.getColumns().add(TableActions.column(
            row -> openEditModal(row, refresh),
            row -> { try { MainScreen.receitaController.delete(Integer.parseInt(row[0])); refresh.run(); }
                     catch (Exception e) { throw new RuntimeException(e); } }
        ));

        VBox root = new VBox(0);
        root.setPadding(new Insets(18, 22, 18, 22));
        root.setStyle(StyleConstants.CONTENT_BG);
        VBox.setVgrow(table, Priority.ALWAYS);
        root.getChildren().add(table);

        List<FormField> newFields = List.of(
            new FormField("nome",      "Nome",        FormField.Type.TEXT,   List.of(), "ex: Cerveja Amber"),
            new FormField("descricao", "Descrição",   FormField.Type.TEXT,   List.of(), "ex: Receita artesanal"),
            new FormField("preco",     "Preço (€/L)", FormField.Type.NUMBER, List.of(), "ex: 2.50")
        );

        return new ScreenBundle(root, table, () -> new ModalOverlay(
            MainScreen.contentArea, "Nova Receita", newFields, values -> {
                String nome = values.get("nome");
                if (nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
                MainScreen.receitaController.create(nome, values.get("descricao"));
                String precoStr = values.get("preco");
                if (!precoStr.isBlank()) {
                    double preco = Double.parseDouble(precoStr.replace(',', '.'));
                    var lista = MainScreen.receitaController.listAll();
                    if (!lista.isEmpty())
                        MainScreen.receitaController.setPrice(lista.get(lista.size()-1).getId(), preco);
                }
                refresh.run();
            }).show());
    }

    private static void openEditModal(String[] row, Runnable refresh) {
        int id = Integer.parseInt(row[0]);
        List<FormField> fields = List.of(
            new FormField("nome",      "Nome",        FormField.Type.TEXT, List.of(), row[1]),
            new FormField("descricao", "Descrição",   FormField.Type.TEXT, List.of(), row[2].equals("—") ? "" : row[2]),
            new FormField("preco",     "Preço (€/L)", FormField.Type.NUMBER, List.of(), "")
        );
        new ModalOverlay(MainScreen.contentArea, "Editar Receita #" + id, fields, values -> {
            MainScreen.receitaController.update(id, values.get("nome"), values.get("descricao"));
            String precoStr = values.get("preco");
            if (!precoStr.isBlank())
                MainScreen.receitaController.setPrice(id, Double.parseDouble(precoStr.replace(',', '.')));
            refresh.run();
        }).show();
    }
}
