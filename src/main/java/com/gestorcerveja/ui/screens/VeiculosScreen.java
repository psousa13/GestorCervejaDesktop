package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.components.TableBuilder;
import javafx.scene.layout.VBox;
import java.sql.SQLException;

public class VeiculosScreen {
    public static VBox build() {
        try {
            var veiculos = MainScreen.veiculoService.getAll();
            String[][] rows = veiculos.stream().map(v -> new String[]{
                    v.getMatricula(),
                    v.getNome(),
                    v.getTipo(),
                    v.getMarca(),
                    v.getCapacidade() + "L",
                    v.getOcupacaoAtual() + "L / " + v.getCapacidade() + "L",
            }).toArray(String[][]::new);
            return TableBuilder.build(
                    new String[]{"Matrícula","Nome","Tipo","Marca","Capacidade","Ocupação"},
                    rows
            );
        } catch (SQLException e) {
            return new VBox(TableBuilder.errorLabel("Erro ao carregar veículos: " + e.getMessage()));
        }
    }
}