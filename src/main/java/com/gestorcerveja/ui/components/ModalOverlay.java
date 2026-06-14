package com.gestorcerveja.ui.components;
import com.gestorcerveja.ui.StyleConstants;
import javafx.geometry.Insets; import javafx.geometry.Pos;
import javafx.scene.control.*; import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import java.util.LinkedHashMap; import java.util.List; import java.util.Map;

public class ModalOverlay {
    private final StackPane root; private final String title; private final List<FormField> fields;
    private final SaveAction onSave; private final Runnable onClose;
    private final StackPane overlay=new StackPane();

    public ModalOverlay(StackPane root,String title,List<FormField> fields,SaveAction onSave){this(root,title,fields,onSave,null);}
    public ModalOverlay(StackPane root,String title,List<FormField> fields,SaveAction onSave,Runnable onClose){
        this.root=root;this.title=title;this.fields=fields;this.onSave=onSave;this.onClose=onClose;
    }

    public void show(){
        root.getChildren().forEach(n->{if(!n.equals(overlay))n.setEffect(new GaussianBlur(6));});
        overlay.setStyle("-fx-background-color:rgba(0,0,0,0.45);"); overlay.setAlignment(Pos.CENTER);
        overlay.getChildren().setAll(buildCard());
        overlay.setOnMouseClicked(e->{if(e.getTarget()==overlay)close();});
        if(!root.getChildren().contains(overlay)) root.getChildren().add(overlay);
    }

    private VBox buildCard(){
        VBox card=new VBox(16); card.setPadding(new Insets(28,32,28,32)); card.setMaxWidth(460);
        card.setStyle("-fx-background-color:"+StyleConstants.BG_DARK+";-fx-background-radius:12;-fx-border-color:"+StyleConstants.ACCENT+";-fx-border-radius:12;-fx-border-width:1.5;");
        Label lbTitle=new Label(title); lbTitle.setStyle("-fx-font-size:16px;-fx-font-weight:bold;-fx-text-fill:"+StyleConstants.ACCENT+";");
        Separator sep=new Separator(); sep.setStyle("-fx-background-color:"+StyleConstants.ACCENT+";-fx-opacity:0.4;");
        Map<String,Control> controls=new LinkedHashMap<>();
        GridPane grid=new GridPane(); grid.setHgap(12); grid.setVgap(12);
        ColumnConstraints c1=new ColumnConstraints(); c1.setPrefWidth(140);
        ColumnConstraints c2=new ColumnConstraints(); c2.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(c1,c2);
        int row=0;
        for(FormField f:fields){
            Label lbl=new Label(f.getLabel()+":"); lbl.setStyle("-fx-text-fill:"+StyleConstants.TEXT_PRIMARY+";-fx-font-size:13px;");
            Control ctrl=buildControl(f); controls.put(f.getKey(),ctrl);
            grid.add(lbl,0,row); grid.add(ctrl,1,row); row++;
        }
        Label lblErr=new Label(); lblErr.setStyle("-fx-text-fill:#e05252;-fx-font-size:12px;"); lblErr.setVisible(false); lblErr.setManaged(false);
        Button btnSave=mkBtn("Guardar",StyleConstants.ACCENT,"#fff");
        Button btnCancel=mkBtn("Cancelar","#444",StyleConstants.TEXT_PRIMARY);
        btnCancel.setOnAction(e->close());
        btnSave.setOnAction(e->{
            Map<String,String> values=new LinkedHashMap<>();
            controls.forEach((k,c)->values.put(k,getVal(c)));
            try{onSave.execute(values);close();}catch(Exception ex){lblErr.setText("Erro: "+ex.getMessage());lblErr.setVisible(true);lblErr.setManaged(true);}
        });
        HBox btns=new HBox(12,btnSave,btnCancel); btns.setAlignment(Pos.CENTER_RIGHT);
        card.getChildren().addAll(lbTitle,sep,grid,lblErr,btns); return card;
    }

    private Control buildControl(FormField f){return switch(f.getType()){case COMBO->{ComboBox<String> cb=new ComboBox<>();cb.getItems().addAll(f.getOptions());if(!f.getOptions().isEmpty())cb.setValue(f.getOptions().get(0));cb.setMaxWidth(Double.MAX_VALUE);styleInp(cb);yield cb;}case PASSWORD->{PasswordField pf=new PasswordField();pf.setPromptText(f.getPlaceholder());styleInp(pf);yield pf;}case DATE->{DatePicker dp=new DatePicker();dp.setPromptText("aaaa-mm-dd");dp.setMaxWidth(Double.MAX_VALUE);dp.setStyle(inpStyle());yield dp;}default->{TextField tf=new TextField();tf.setPromptText(f.getPlaceholder().isBlank()?f.getLabel():f.getPlaceholder());styleInp(tf);yield tf;}};}
    private String getVal(Control c){if(c instanceof TextField tf)return tf.getText().trim();if(c instanceof PasswordField pf)return pf.getText().trim();if(c instanceof ComboBox<?> cb)return cb.getValue()==null?"":cb.getValue().toString();if(c instanceof DatePicker dp)return dp.getValue()==null?"":dp.getValue().toString();return "";}
    private void styleInp(Control c){c.setStyle(inpStyle());if(c instanceof Region r)r.setMaxWidth(Double.MAX_VALUE);}
    private String inpStyle(){return "-fx-background-color:"+StyleConstants.BG_MID+";-fx-text-fill:"+StyleConstants.TEXT_PRIMARY+";-fx-prompt-text-fill:#888;-fx-border-color:#555;-fx-border-radius:6;-fx-background-radius:6;-fx-padding:6 10;";}
    private Button mkBtn(String text,String bg,String fg){Button b=new Button(text);b.setPrefWidth(110);b.setStyle("-fx-background-color:"+bg+";-fx-text-fill:"+fg+";-fx-font-size:13px;-fx-font-weight:bold;-fx-background-radius:8;-fx-cursor:hand;-fx-padding:8 18;");return b;}
    private void close(){root.getChildren().remove(overlay);root.getChildren().forEach(n->n.setEffect(null));if(onClose!=null)onClose.run();}
}
