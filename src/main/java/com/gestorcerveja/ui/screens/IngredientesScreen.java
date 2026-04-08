package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.components.TableBuilder;
import javafx.scene.layout.VBox;
import java.sql.SQLException;

public class IngredientesScreen {
    public static VBox build() {
        try {
            var ingredientes = MainScreen.ingredienteService.getAll();
            String[][] rows = ingredientes.stream().map(i -> new String[]{
                    i.getNome(),
                    i.getUnidade() != null ? i.getUnidade() : "—",
                    String.valueOf(i.getStockAtual()),
                    String.valueOf(i.getStockMinimo()),
                    i.getStockAtual() < i.getStockMinimo() ? "Crítico" : "OK",
            }).toArray(String[][]::new);
            return TableBuilder.build(
                    new String[]{"Nome","Unidade","Stock Atual","Stock Mínimo","Estado"},
                    rows
            );
        } catch (SQLException e) {
            return new VBox(TableBuilder.errorLabel("Erro ao carregar ingredientes: " + e.getMessage()));
        }
    }
}