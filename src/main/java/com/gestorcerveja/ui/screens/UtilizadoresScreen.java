package com.gestorcerveja.ui.screens;
import com.gestorcerveja.ui.StyleConstants;
import com.gestorcerveja.ui.components.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import java.util.List;

public class UtilizadoresScreen {
    public static ScreenBundle build() {
        TableView<String[]> table=TableBuilder.buildTable(new String[]{"ID","Nome","Role"},new String[0][]);
        Runnable refresh=()->{
            try{table.setItems(FXCollections.observableArrayList(MainScreen.usuarioController.listAll().stream().map(u->new String[]{
                String.valueOf(u.getId()),u.getNome(),String.valueOf(u.getIdrole())
            }).toList()));}catch(Exception e){table.setPlaceholder(TableBuilder.errorLabel("Erro: "+e.getMessage()));}
        };
        refresh.run();
        table.getColumns().add(TableActions.column(row->openEditModal(row,refresh),
            row->{try{MainScreen.usuarioController.delete(Integer.parseInt(row[0]));refresh.run();}catch(Exception e){throw new RuntimeException(e);}}));
        VBox root=new VBox(0);root.setPadding(new Insets(18,22,18,22));root.setStyle(StyleConstants.CONTENT_BG);VBox.setVgrow(table,Priority.ALWAYS);root.getChildren().add(table);
        List<String> roleOpts=getRoleOpts();
        List<FormField> nf=List.of(
            new FormField("nome","Nome",FormField.Type.TEXT),
            new FormField("senha","Senha",FormField.Type.PASSWORD),
            new FormField("idrole","Role",roleOpts.isEmpty()?FormField.Type.NUMBER:FormField.Type.COMBO,roleOpts)
        );
        return new ScreenBundle(root,table,()->new ModalOverlay(MainScreen.contentArea,"Novo Utilizador",nf,values->{
            String nome=values.get("nome"),senha=values.get("senha");
            if(nome.isBlank()) throw new IllegalArgumentException("Nome obrigatório.");
            if(senha.isBlank()) throw new IllegalArgumentException("Senha obrigatória.");
            String rr=values.get("idrole"); int idrole=pi(rr.contains("–")?rr.split("–")[0].trim():rr,1);
            MainScreen.usuarioController.create(nome,senha,idrole); refresh.run();
        }).show());
    }
    private static void openEditModal(String[] row,Runnable refresh){
        int id=pi(row[0],0);
        List<FormField> fields=List.of(
            new FormField("nome","Nome",FormField.Type.TEXT,List.of(),row[1]),
            new FormField("novaSenha","Nova senha",FormField.Type.PASSWORD,List.of(),"Deixar vazio = não alterar"),
            new FormField("confirmar","Confirmar",FormField.Type.PASSWORD)
        );
        new ModalOverlay(MainScreen.contentArea,"Editar Utilizador #"+id,fields,values->{
            String ns=values.get("novaSenha"),cf=values.get("confirmar");
            if(!ns.isBlank()&&!ns.equals(cf)) throw new IllegalArgumentException("Senhas não coincidem.");
            MainScreen.usuarioController.updateSelf(id,values.get("nome"),ns); refresh.run();
        }).show();
    }
    private static List<String> getRoleOpts(){try{return MainScreen.roleController.listAll().stream().map(r->r.getId()+" – "+r.getNome()).toList();}catch(Exception e){return List.of();}}
    private static int pi(String s,int fb){try{return Integer.parseInt(s.trim());}catch(Exception e){return fb;}}
}
