package com.gestorcerveja.ui.components;
import javafx.scene.control.TableView; import javafx.scene.layout.VBox;
public record ScreenBundle(VBox view, TableView<String[]> table, Runnable onNew) {
    public static ScreenBundle viewOnly(VBox view){return new ScreenBundle(view,null,null);}
    public static ScreenBundle exportOnly(VBox view,TableView<String[]> table){return new ScreenBundle(view,table,null);}
}
