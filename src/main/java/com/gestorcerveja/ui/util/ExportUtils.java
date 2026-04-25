package com.gestorcerveja.ui.util;

import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utilitário de exportação de tabelas JavaFX.
 * Suporta CSV e TXT (colunas alinhadas por tabulação).
 */
public final class ExportUtils {

    private ExportUtils() {}

    /**
     * Abre um FileChooser e exporta todos os dados visíveis na tabela.
     *
     * @param table  a TableView cujos dados serão exportados
     * @param owner  janela pai para o FileChooser
     * @param baseName nome sugerido para o ficheiro (sem extensão)
     */
    public static <T> void export(TableView<T> table, Window owner, String baseName) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Exportar dados");
        fc.setInitialFileName(baseName);
        fc.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("CSV (separado por vírgulas)", "*.csv"),
            new FileChooser.ExtensionFilter("Texto (separado por tabs)",   "*.txt")
        );

        File file = fc.showSaveDialog(owner);
        if (file == null) return;

        boolean isCsv = file.getName().toLowerCase().endsWith(".csv");

        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            List<TableColumn<T, ?>> cols = getLeafColumns(table);

            // cabeçalho
            pw.println(buildRow(
                cols.stream().map(TableColumn::getText).collect(Collectors.toList()),
                isCsv
            ));

            // linhas
            for (T item : table.getItems()) {
                List<String> vals = cols.stream()
                    .map(col -> {
                        var cellData = col.getCellObservableValue(item);
                        if (cellData == null) return "";
                        Object v = cellData.getValue();
                        return v == null ? "" : v.toString();
                    })
                    .collect(Collectors.toList());
                pw.println(buildRow(vals, isCsv));
            }

            showInfo("Exportação concluída", "Ficheiro guardado em:\n" + file.getAbsolutePath());

        } catch (IOException ex) {
            showError("Erro ao exportar", ex.getMessage());
        }
    }

    // ------------------------------------------------------------------ helpers

    private static String buildRow(List<String> cells, boolean csv) {
        if (csv) {
            return cells.stream()
                .map(v -> "\"" + v.replace("\"", "\"\"") + "\"")
                .collect(Collectors.joining(","));
        } else {
            return String.join("\t", cells);
        }
    }

    /** Obtém apenas colunas folha (sem filhas), para tabelas com sub-colunas. */
    @SuppressWarnings("unchecked")
    private static <T> List<TableColumn<T, ?>> getLeafColumns(TableView<T> table) {
        return table.getColumns().stream()
            .flatMap(col -> getLeaves((TableColumn<T, ?>) col).stream())
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private static <T> List<TableColumn<T, ?>> getLeaves(TableColumn<T, ?> col) {
        if (col.getColumns().isEmpty()) return List.of(col);
        return col.getColumns().stream()
            .flatMap(c -> getLeaves((TableColumn<T, ?>) c).stream())
            .collect(Collectors.toList());
    }

    private static void showInfo(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private static void showError(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
