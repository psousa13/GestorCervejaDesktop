package com.gestorcerveja.ui.screens;
import com.gestorcerveja.ui.JavaFXApplication;
import com.gestorcerveja.ui.SessionManager;
import com.gestorcerveja.ui.StyleConstants;
import com.gestorcerveja.ui.components.*;
import javafx.geometry.Insets; import javafx.geometry.Pos;
import javafx.scene.control.*; import javafx.scene.layout.*;
import javafx.scene.paint.Color; import javafx.scene.shape.Circle;
import java.util.Arrays; import java.util.List;

public class PerfilScreen {
    public static VBox build() {
        VBox root=new VBox(0); root.setStyle(StyleConstants.CONTENT_BG); root.setAlignment(Pos.TOP_CENTER); root.setPadding(new Insets(40));
        VBox card=new VBox(0); card.setMaxWidth(560);
        card.setStyle("-fx-background-color:white;-fx-border-color:#E8E2DF;-fx-border-width:1px;-fx-border-radius:14px;-fx-background-radius:14px;");
        card.getChildren().addAll(buildHeader(),sep(),buildInfoSection(),sep(),buildActionsSection());
        root.getChildren().add(card); return root;
    }
    private static VBox buildHeader(){
        VBox h=new VBox(12); h.setAlignment(Pos.CENTER); h.setPadding(new Insets(36,32,28,32));
        StackPane av=buildAvatar(56);
        Label nm=new Label(SessionManager.getUserNome()); nm.setStyle("-fx-font-size:20px;-fx-font-weight:bold;-fx-text-fill:#1A1614;");
        Label rl=new Label(SessionManager.getRoleLabel()); rl.setStyle("-fx-background-color:#F5EEF0;-fx-text-fill:#7B2D3E;-fx-background-radius:20px;-fx-padding:3px 12px;-fx-font-size:11px;");
        h.getChildren().addAll(av,nm,rl); return h;
    }
    private static VBox buildInfoSection(){
        VBox s=new VBox(0); s.setPadding(new Insets(20,32,20,32));
        Label t=new Label("INFORMAÇÃO DA CONTA"); t.setStyle("-fx-font-size:9.5px;-fx-font-weight:bold;-fx-text-fill:#8C8480;"); VBox.setMargin(t,new Insets(0,0,14,0)); s.getChildren().add(t);
        var user=SessionManager.getUser();
        s.getChildren().addAll(infoRow("Utilizador",user!=null?user.getNome():SessionManager.getUserNome(),"👤"),infoRow("Role",SessionManager.getRoleLabel(),"🔑"),infoRow("ID","#"+(user!=null?user.getId():"—"),"🆔"));
        return s;
    }
    private static HBox infoRow(String label,String value,String icon){
        HBox row=new HBox(12); row.setAlignment(Pos.CENTER_LEFT); row.setPadding(new Insets(11,0,11,0)); row.setStyle("-fx-border-color:#F0EBE8;-fx-border-width:0 0 1px 0;");
        Label ico=new Label(icon); ico.setMinWidth(22); ico.setStyle("-fx-font-size:14px;");
        VBox info=new VBox(2); Label lbl=new Label(label); lbl.setStyle("-fx-font-size:10px;-fx-text-fill:#8C8480;"); Label val=new Label(value); val.setStyle("-fx-font-size:13px;-fx-text-fill:#1A1614;-fx-font-weight:bold;"); info.getChildren().addAll(lbl,val);
        row.getChildren().addAll(ico,info); return row;
    }
    private static VBox buildActionsSection(){
        VBox s=new VBox(10); s.setPadding(new Insets(24,32,28,32));
        Label t=new Label("AÇÕES"); t.setStyle("-fx-font-size:9.5px;-fx-font-weight:bold;-fx-text-fill:#8C8480;"); VBox.setMargin(t,new Insets(0,0,4,0));
        Button btnEdit=btn("✏  Editar Perfil",StyleConstants.BTN_PRIMARY);
        Button btnLogout=btn("→  Terminar Sessão","-fx-background-color:white;-fx-text-fill:#A32D2D;-fx-border-color:#F5CECE;-fx-border-width:1px;-fx-border-radius:7px;-fx-background-radius:7px;-fx-font-size:12px;-fx-font-weight:bold;-fx-padding:8px 13px;-fx-cursor:hand;");
        btnEdit.setOnAction(e->showEditModal());
        btnLogout.setOnAction(e->{Alert a=new Alert(Alert.AlertType.CONFIRMATION);a.setTitle("Terminar Sessão");a.setHeaderText(null);a.setContentText("Tem a certeza?");a.showAndWait().ifPresent(b->{if(b==ButtonType.OK){SessionManager.clear();JavaFXApplication.showLogin();}});});
        s.getChildren().addAll(t,btnEdit,btnLogout); return s;
    }
    private static void showEditModal(){
        var user=SessionManager.getUser(); if(user==null) return;
        List<FormField> fields=List.of(new FormField("nome","Nome",FormField.Type.TEXT,List.of(),user.getNome()),new FormField("novaSenha","Nova senha",FormField.Type.PASSWORD,List.of(),"Vazio = não alterar"),new FormField("confirmar","Confirmar",FormField.Type.PASSWORD));
        new ModalOverlay(MainScreen.contentArea,"Editar Perfil",fields,values->{
            String novo=values.get("novaSenha"),cf=values.get("confirmar");
            if(!novo.isBlank()&&!novo.equals(cf)) throw new IllegalArgumentException("Senhas não coincidem.");
            MainScreen.usuarioController.updateSelf(user.getId(),values.get("nome"),novo);
            user.setNome(values.get("nome")); if(!novo.isBlank()) user.setSenha(novo);
            SessionManager.setUser(user); MainScreen.navigateTo("perfil");
        },()->MainScreen.navigateTo("perfil")).show();
    }
    public static StackPane buildAvatar(double r){
        String init=Arrays.stream(SessionManager.getUserNome().split(" ")).limit(2).map(w->w.isBlank()?"":String.valueOf(w.charAt(0))).reduce("",String::concat).toUpperCase();
        if(init.isBlank()) init="U";
        Circle ring=new Circle(r+4); ring.setFill(Color.TRANSPARENT); ring.setStroke(Color.web("#F5EEF0")); ring.setStrokeWidth(2);
        Circle circle=new Circle(r,Color.web("#7B2D3E"));
        Label lb=new Label(init); lb.setStyle("-fx-text-fill:white;-fx-font-size:"+(r*0.55)+"px;-fx-font-weight:bold;");
        StackPane sp=new StackPane(ring,circle,lb); sp.setMaxSize((r+4)*2,(r+4)*2); return sp;
    }
    private static Button btn(String text,String style){Button b=new Button(text);b.setMaxWidth(Double.MAX_VALUE);b.setStyle(style);return b;}
    private static Separator sep(){Separator s=new Separator();s.setStyle("-fx-background-color:#F0EBE8;");return s;}
}
