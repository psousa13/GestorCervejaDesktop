package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.components.*;
import com.gestorcerveja.ui.StyleConstants;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.List;

public class VeiculosScreen {

    public static ScreenBundle build() {
        TableView<String[]> table = TableBuilder.buildTable(
                new String[]{"Matrícula", "Nome", "Tipo", "Marca", "Capacidade", "Ocupação"},
                new String[0][]);

        Runnable refresh = () -> {
            try {
                var list = MainScreen.veiculoController.listAll();
                table.setItems(FXCollections.observableArrayList(
                        list.stream().map(v -> new String[]{
                                v.getMatricula(),
                                v.getNome(),
                                v.getTipo(),
                                v.getMarca(),
                                v.getCapacidade() + "L",
                                v.getOcupacaoAtual() + "L / " + v.getCapacidade() + "L"
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
                new FormField("matricula",  "Matrícula",   FormField.Type.TEXT,  List.of(), "ex: AA-00-AA"),
                new FormField("nome",       "Nome",        FormField.Type.TEXT,  List.of(), "ex: Tanque Alpha"),
                new FormField("tipo",       "Tipo",        FormField.Type.COMBO,
                        List.of("Tanque", "Barril", "Cisterna", "Outro")),
                new FormField("marca",      "Marca",       FormField.Type.TEXT,  List.of(), "ex: Speidel"),
                new FormField("cor",        "Cor",         FormField.Type.TEXT,  List.of(), "ex: Prata"),
                new FormField("capacidade", "Capacidade (L)", FormField.Type.NUMBER, List.of(), "ex: 500")
        );

        Runnable onNew = () -> new ModalOverlay(
                MainScreen.contentArea,
                "Novo Veículo / Contentor",
                fields,
                values -> {
                    String mat = values.get("matricula");
                    if (mat.isBlank()) throw new IllegalArgumentException("Matrícula é obrigatória.");
                    double cap = parseDouble(values.get("capacidade"), 0);
                    MainScreen.veiculoController.create(
                            mat,
                            values.get("marca"),
                            values.get("cor"),
                            values.get("nome"),
                            cap,
                            values.get("tipo"));
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
