package com.gestorcerveja.ui.screens;
import com.gestorcerveja.ui.SessionManager;
import com.gestorcerveja.ui.StyleConstants;
import com.gestorcerveja.ui.components.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import java.util.List;

public class ProducaoScreen {
    public static ScreenBundle build() {
        TableView<String[]> table=TableBuilder.buildTable(new String[]{"ID","Request","Utilizador","Estado","Criado em","Conclusão"},new String[0][]);
        Runnable refresh=()->{
            try{table.setItems(FXCollections.observableArrayList(MainScreen.requestController.listAll().stream().map(r->new String[]{
                String.valueOf(r.getId()),"#REQ-"+String.format("%03d",r.getId()),String.valueOf(r.getIdusuario()),
                r.getEstado(),r.getDataCriacao()!=null?r.getDataCriacao().toString():"—",r.getDataConclusao()!=null?r.getDataConclusao().toString():"—"
            }).toList()));}catch(Exception e){table.setPlaceholder(TableBuilder.errorLabel("Erro: "+e.getMessage()));}
        };
        refresh.run();
        table.getColumns().add(TableActions.column(row->{
            int id=Integer.parseInt(row[0]);
            List<FormField> fields=List.of(new FormField("estado","Estado",FormField.Type.COMBO,List.of("Pendente","Em Progresso","Concluido","Cancelado")));
            new ModalOverlay(MainScreen.contentArea,"Editar #REQ-"+String.format("%03d",id),fields,values->{
                if("Concluido".equals(values.get("estado"))) MainScreen.requestController.concluir(id);
                refresh.run();
            }).show();
        }, row->{throw new UnsupportedOperationException("Requests não podem ser apagados directamente.");}));
        table.getColumns().get(0).setVisible(false);
        VBox root=new VBox(0);root.setPadding(new Insets(18,22,18,22));root.setStyle(StyleConstants.CONTENT_BG);VBox.setVgrow(table,Priority.ALWAYS);root.getChildren().add(table);
        var user=SessionManager.getUser(); int idU=user!=null?user.getId():1;
        return new ScreenBundle(root,table,()->new ModalOverlay(MainScreen.contentArea,"Nova Etapa de Produção",
            List.of(new FormField("idusuario","ID Utilizador",FormField.Type.NUMBER,List.of(),String.valueOf(idU))),
            values->{MainScreen.requestController.create(pi(values.get("idusuario"),idU));refresh.run();}).show());
    }
    private static int pi(String s,int fb){try{return Integer.parseInt(s.trim());}catch(Exception e){return fb;}}
}
