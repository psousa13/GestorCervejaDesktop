package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.components.*;
import com.gestorcerveja.ui.StyleConstants;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class LotesScreen {

    public static ScreenBundle build() {
        TableView<String[]> table = TableBuilder.buildTable(
                new String[]{"Lote", "Receita", "Litros", "Data Produção", "Veículo"},
                new String[0][]);

        Runnable refresh = () -> {
            try {
                var list = MainScreen.loteController.listAll();
                table.setItems(FXCollections.observableArrayList(
                        list.stream().map(l -> new String[]{
                                "#" + l.getId(),
                                String.valueOf(l.getIdreceita()),
                                l.getLitros() + "L",
                                l.getDataProducao() != null ? l.getDataProducao().toString() : "—",
                                String.valueOf(l.getIdveiculo())
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
                new FormField("idpedido",          "ID Pedido",         FormField.Type.NUMBER, List.of(), "ex: 1"),
                new FormField("idreceita",         "ID Receita",        FormField.Type.NUMBER, List.of(), "ex: 2"),
                new FormField("litros",            "Litros",            FormField.Type.NUMBER, List.of(), "ex: 100"),
                new FormField("dataProducao",      "Data Produção",     FormField.Type.DATE),
                new FormField("idveiculo",         "ID Veículo",        FormField.Type.NUMBER, List.of(), "ex: 1"),
                new FormField("idrequest",         "ID Request Prod.",  FormField.Type.NUMBER, List.of(), "ex: 1")
        );

        Runnable onNew = () -> new ModalOverlay(
                MainScreen.contentArea,
                "Novo Lote de Produção",
                fields,
                values -> {
                    int    idped  = parseInt(values.get("idpedido"),  0);
                    int    idrec  = parseInt(values.get("idreceita"), 0);
                    double litros = parseDouble(values.get("litros"),  0);
                    LocalDate data = values.get("dataProducao").isBlank()
                            ? LocalDate.now()
                            : LocalDate.parse(values.get("dataProducao"));
                    int idveic  = parseInt(values.get("idveiculo"), 0);
                    int idreq   = parseInt(values.get("idrequest"),  0);
                    MainScreen.loteController.create(idped, idrec, litros, data, idveic, idreq);
                    refresh.run();
                }
        ).show();

        return new ScreenBundle(root, table, onNew);
    }

    private static int parseInt(String s, int fallback) {
        try { return Integer.parseInt(s.trim()); } catch (NumberFormatException e) { return fallback; }
    }
    private static double parseDouble(String s, double fallback) {
        try { return Double.parseDouble(s.replace(',', '.')); } catch (NumberFormatException e) { return fallback; }
    }
}
