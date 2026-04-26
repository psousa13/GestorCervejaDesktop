package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.StyleConstants;
import com.gestorcerveja.ui.components.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.util.List;

public class VeiculosScreen {

    public static ScreenBundle build() {
        TableView<String[]> table = TableBuilder.buildTable(
                new String[]{"ID", "Matrícula", "Nome", "Tipo", "Marca", "Capacidade", "Ocupação"}, new String[0][]);

        Runnable refresh = () -> {
            try {
                table.setItems(FXCollections.observableArrayList(
                    MainScreen.veiculoController.listAll().stream().map(v -> new String[]{
                        String.valueOf(v.getId()), v.getMatricula(), v.getNome(), v.getTipo(), v.getMarca(),
                        v.getCapacidade() + "L", v.getOcupacaoAtual() + "L / " + v.getCapacidade() + "L"
                    }).toList()));
            } catch (SQLException e) { table.setPlaceholder(TableBuilder.errorLabel("Erro: " + e.getMessage())); }
        };
        refresh.run();

        table.getColumns().add(TableActions.column(
            row -> openEditModal(row, refresh),
            row -> { try { MainScreen.veiculoController.delete(Integer.parseInt(row[0])); refresh.run(); }
                     catch (Exception e) { throw new RuntimeException(e); } }
        ));

        VBox root = new VBox(0);
        root.setPadding(new Insets(18, 22, 18, 22));
        root.setStyle(StyleConstants.CONTENT_BG);
        VBox.setVgrow(table, Priority.ALWAYS);
        root.getChildren().add(table);

        List<FormField> newFields = List.of(
            new FormField("matricula",  "Matrícula",      FormField.Type.TEXT,  List.of(), "ex: AA-00-AA"),
            new FormField("nome",       "Nome",           FormField.Type.TEXT,  List.of(), "ex: Tanque Alpha"),
            new FormField("tipo",       "Tipo",           FormField.Type.COMBO, List.of("Tanque","Barril","Cisterna","Camião","Carrinha","Outro")),
            new FormField("marca",      "Marca",          FormField.Type.TEXT,  List.of(), "ex: Speidel"),
            new FormField("cor",        "Cor",            FormField.Type.TEXT,  List.of(), "ex: Prata"),
            new FormField("capacidade", "Capacidade (L)", FormField.Type.NUMBER,List.of(), "ex: 500")
        );

        return new ScreenBundle(root, table, () -> new ModalOverlay(
            MainScreen.contentArea, "Novo Veículo / Contentor", newFields, values -> {
                String mat = values.get("matricula");
                if (mat.isBlank()) throw new IllegalArgumentException("Matrícula é obrigatória.");
                MainScreen.veiculoController.create(mat, values.get("marca"), values.get("cor"),
                    values.get("nome"), parseDouble(values.get("capacidade")), values.get("tipo"));
                refresh.run();
            }).show());
    }

    private static void openEditModal(String[] row, Runnable refresh) {
        int id = Integer.parseInt(row[0]);
        List<FormField> fields = List.of(
            new FormField("nome",       "Nome",           FormField.Type.TEXT,  List.of(), row[2]),
            new FormField("tipo",       "Tipo",           FormField.Type.COMBO, List.of("Tanque","Barril","Cisterna","Camião","Carrinha","Outro")),
            new FormField("marca",      "Marca",          FormField.Type.TEXT,  List.of(), row[4]),
            new FormField("cor",        "Cor",            FormField.Type.TEXT,  List.of(), ""),
            new FormField("capacidade", "Capacidade (L)", FormField.Type.NUMBER,List.of(), row[5].replace("L",""))
        );
        new ModalOverlay(MainScreen.contentArea, "Editar Veículo #" + id, fields, values -> {
            MainScreen.veiculoController.update(id, values.get("nome"), values.get("tipo"),
                values.get("marca"), values.get("cor"), parseDouble(values.get("capacidade")));
            refresh.run();
        }).show();
    }

    private static double parseDouble(String s) {
        try { return Double.parseDouble(s.replace(',','.')); } catch (NumberFormatException e) { return 0; }
    }
}
