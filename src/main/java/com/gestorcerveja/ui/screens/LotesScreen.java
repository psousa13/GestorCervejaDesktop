package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.components.TableBuilder;
import javafx.scene.layout.VBox;
import java.sql.SQLException;

public class LotesScreen {
    public static VBox build() {
        try {
            var lotes = MainScreen.loteService.getAll();
            String[][] rows = lotes.stream().map(l -> new String[]{
                    "#" + l.getId(),
                    String.valueOf(l.getIdreceita()),
                    l.getLitros() + "L",
                    l.getDataProducao() != null ? l.getDataProducao().toString() : "—",
                    String.valueOf(l.getIdveiculo()),
            }).toArray(String[][]::new);
            return TableBuilder.build(
                    new String[]{"Lote","Receita","Litros","Data Produção","Veículo"},
                    rows
            );
        } catch (SQLException e) {
            return new VBox(TableBuilder.errorLabel("Erro ao carregar lotes: " + e.getMessage()));
        }
    }
}