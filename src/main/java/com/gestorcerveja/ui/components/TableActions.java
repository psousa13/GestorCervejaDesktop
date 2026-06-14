package com.gestorcerveja.ui.components;
import com.gestorcerveja.ui.StyleConstants;
import javafx.geometry.Pos; import javafx.scene.control.*;
import javafx.scene.layout.HBox; import java.util.function.Consumer;

public class TableActions {
    public static TableColumn<String[],Void> column(Consumer<String[]> onEdit,Consumer<String[]> onDelete){
        TableColumn<String[],Void> col=new TableColumn<>("Ações");col.setSortable(false);col.setResizable(false);col.setPrefWidth(130);
        col.setCellFactory(tc->new TableCell<>(){
            private final Button btnEdit=mkBtn("✏ Editar",StyleConstants.BTN_SECONDARY);
            private final Button btnDel=mkBtn("✕ Apagar",delStyle());
            private final HBox box=new HBox(6,btnEdit,btnDel);
            {box.setAlignment(Pos.CENTER);
             btnEdit.setOnAction(e->{String[] row=getTableView().getItems().get(getIndex());onEdit.accept(row);});
             btnDel.setOnAction(e->{String[] row=getTableView().getItems().get(getIndex());Alert a=new Alert(Alert.AlertType.CONFIRMATION);a.setTitle("Confirmar");a.setHeaderText(null);a.setContentText("Apagar este registo?");a.showAndWait().ifPresent(b->{if(b==ButtonType.OK)try{onDelete.accept(row);}catch(Exception ex){Alert err=new Alert(Alert.AlertType.ERROR);err.setContentText(ex.getMessage());err.show();}});});}
            @Override protected void updateItem(Void v,boolean empty){super.updateItem(v,empty);setGraphic(empty?null:box);}
        });
        return col;
    }
    private static Button mkBtn(String text,String style){Button b=new Button(text);b.setStyle(style);return b;}
    private static String delStyle(){return "-fx-background-color:white;-fx-text-fill:#A32D2D;-fx-border-color:#F5CECE;-fx-border-width:1px;-fx-border-radius:7px;-fx-background-radius:7px;-fx-font-size:11px;-fx-padding:4px 8px;-fx-cursor:hand;";}
}
