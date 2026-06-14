package com.gestorcerveja.ui.screens;
import com.gestorcerveja.ui.StyleConstants;
import com.gestorcerveja.ui.components.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import java.time.LocalDate; import java.util.List;

public class LotesScreen {
    public static ScreenBundle build() {
        TableView<String[]> table=TableBuilder.buildTable(new String[]{"ID","Lote","Receita","Litros","Data Produção","Veículo"},new String[0][]);
        Runnable refresh=()->{
            try{table.setItems(FXCollections.observableArrayList(MainScreen.loteController.listAll().stream().map(l->new String[]{
                String.valueOf(l.getId()),"#"+l.getId(),String.valueOf(l.getIdreceita()),l.getLitros()+"L",
                l.getDataProducao()!=null?l.getDataProducao().toString():"—",String.valueOf(l.getIdveiculo())
            }).toList()));}catch(Exception e){table.setPlaceholder(TableBuilder.errorLabel("Erro: "+e.getMessage()));}
        };
        refresh.run();
        table.getColumns().add(TableActions.column(row->openEditModal(row,refresh),
            row->{try{MainScreen.loteController.delete(pi(row[0]));refresh.run();}catch(Exception e){throw new RuntimeException(e);}}));
        table.getColumns().get(0).setVisible(false);
        VBox root=new VBox(0);root.setPadding(new Insets(18,22,18,22));root.setStyle(StyleConstants.CONTENT_BG);VBox.setVgrow(table,Priority.ALWAYS);root.getChildren().add(table);
        List<FormField> fields=List.of(
            new FormField("idpedido","ID Pedido",FormField.Type.NUMBER,List.of(),"ex: 1"),
            new FormField("idreceita","ID Receita",FormField.Type.NUMBER,List.of(),"ex: 2"),
            new FormField("litros","Litros",FormField.Type.NUMBER,List.of(),"ex: 100"),
            new FormField("dataProducao","Data Produção",FormField.Type.DATE),
            new FormField("idveiculo","ID Veículo",FormField.Type.NUMBER,List.of(),"ex: 1"),
            new FormField("idrequest","ID Request",FormField.Type.NUMBER,List.of(),"ex: 1")
        );
        return new ScreenBundle(root,table,()->new ModalOverlay(MainScreen.contentArea,"Novo Lote",fields,values->{
            LocalDate data=values.get("dataProducao").isBlank()?LocalDate.now():LocalDate.parse(values.get("dataProducao"));
            MainScreen.loteController.create(pi(values.get("idpedido")),pi(values.get("idreceita")),pd(values.get("litros")),data,pi(values.get("idveiculo")),pi(values.get("idrequest")));
            refresh.run();
        }).show());
    }
    private static void openEditModal(String[] row,Runnable refresh){
        int id=pi(row[0]);
        List<FormField> fields=List.of(new FormField("idveiculo","ID Veículo",FormField.Type.NUMBER,List.of(),row[5]));
        new ModalOverlay(MainScreen.contentArea,"Editar Lote #"+id,fields,values->{
            MainScreen.loteController.updateVeiculo(id,pi(values.get("idveiculo"))); refresh.run();
        }).show();
    }
    private static int pi(String s){try{return Integer.parseInt(s.trim());}catch(Exception e){return 0;}}
    private static double pd(String s){try{return Double.parseDouble(s.replace(',','.'));}catch(Exception e){return 0;}}
}
