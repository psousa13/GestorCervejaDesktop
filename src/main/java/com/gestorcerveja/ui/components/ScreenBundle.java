package com.gestorcerveja.ui.components;

import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

/**
 * Agrupa os elementos retornados por cada screen:
 * <ul>
 *   <li>{@code view}  – o layout raiz a colocar no contentArea</li>
 *   <li>{@code table} – a TableView para exportação (pode ser {@code null})</li>
 *   <li>{@code onNew} – callback do botão "+ Novo" ({@code null} = esconder botão)</li>
 * </ul>
 */
public record ScreenBundle(VBox view, TableView<String[]> table, Runnable onNew) {

    /** Conveniente para screens sem tabela (Dashboard, stubs). */
    public static ScreenBundle viewOnly(VBox view) {
        return new ScreenBundle(view, null, null);
    }

    /** Conveniente para screens com tabela mas sem criar registos (Faturas). */
    public static ScreenBundle exportOnly(VBox view, TableView<String[]> table) {
        return new ScreenBundle(view, table, null);
    }
}
