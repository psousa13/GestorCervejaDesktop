package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.components.*;
import com.gestorcerveja.ui.StyleConstants;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.List;

public class ReceitasScreen {

    public static ScreenBundle build() {
        TableView<String[]> table = TableBuilder.buildTable(
                new String[]{"ID", "Nome", "Descrição", "Preço/L"},
                new String[0][]);

        Runnable refresh = () -> {
            try {
                var list = MainScreen.receitaController.listAll();
                table.setItems(FXCollections.observableArrayList(
                        list.stream().map(r -> {
                            String preco;
                            try {
                                preco = String.format("€ %.2f/L",
                                        MainScreen.receitaController.getActivePrice(r.getId()));
                            } catch (Exception ex) { preco = "Sem preço"; }
                            return new String[]{
                                    String.valueOf(r.getId()),
                                    r.getNome(),
                                    r.getDescricao() != null ? r.getDescricao() : "—",
                                    preco
                            };
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

        List<FormField> fields = List.of(
                new FormField("nome",      "Nome",       FormField.Type.TEXT, List.of(), "ex: Cerveja Amber"),
                new FormField("descricao", "Descrição",  FormField.Type.TEXT, List.of(), "ex: Receita artesanal"),
                new FormField("preco",     "Preço (€/L)", FormField.Type.NUMBER, List.of(), "ex: 2.50")
        );

        Runnable onNew = () -> new ModalOverlay(
                MainScreen.contentArea,
                "Nova Receita",
                fields,
                values -> {
                    String nome = values.get("nome");
                    if (nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
                    MainScreen.receitaController.create(nome, values.get("descricao"));
                    // definir preço se fornecido
                    String precoStr = values.get("preco");
                    if (!precoStr.isBlank()) {
                        try {
                            double preco = Double.parseDouble(precoStr.replace(',', '.'));
                            // busca o id da receita recém-criada (ultima da lista)
                            var lista = MainScreen.receitaController.listAll();
                            if (!lista.isEmpty()) {
                                int idNova = lista.get(lista.size() - 1).getId();
                                MainScreen.receitaController.setPrice(idNova, preco);
                            }
                        } catch (NumberFormatException ignored) {}
                    }
                    refresh.run();
                }
        ).show();

        return new ScreenBundle(root, table, onNew);
    }
}
