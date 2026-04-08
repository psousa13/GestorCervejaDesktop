package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.components.TableBuilder;
import javafx.scene.layout.VBox;
import java.sql.SQLException;

public class ReceitasScreen {
    public static VBox build() {
        try {
            var receitas = MainScreen.receitaService.getAll();
            String[][] rows = receitas.stream().map(r -> {
                String preco;
                try { preco = String.format("€ %.2f/L", MainScreen.receitaService.getActivePrice(r.getId())); }
                catch (Exception ex) { preco = "Sem preço"; }
                return new String[]{
                        String.valueOf(r.getId()),
                        r.getNome(),
                        r.getDescricao() != null ? r.getDescricao() : "—",
                        preco,
                };
            }).toArray(String[][]::new);
            return TableBuilder.build(
                    new String[]{"ID","Nome","Descrição","Preço/L"},
                    rows
            );
        } catch (SQLException e) {
            return new VBox(TableBuilder.errorLabel("Erro ao carregar receitas: " + e.getMessage()));
        }
    }
}