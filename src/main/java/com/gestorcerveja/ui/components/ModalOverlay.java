package com.gestorcerveja.ui.components;

import com.gestorcerveja.ui.StyleConstants;
import com.gestorcerveja.ui.util.SaveAction;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Overlay modal reutilizável.
 * Aparece por cima do conteúdo da screen; o fundo fica desfocado/acinzentado.
 *
 * Uso:
 *   ModalOverlay modal = new ModalOverlay(rootPane, "Novo Ingrediente", fields, values -> {
 *       controller.create(...);
 *       table.refresh();
 *   });
 *   modal.show();
 */
public class ModalOverlay {

    private final StackPane             root;
    private final String                title;
    private final List<FormField>       fields;
    private final SaveAction            onSave;
    private final Runnable              onClose;

    /** Painel escuro semi-transparente que cobre tudo */
    private final StackPane overlay = new StackPane();

    public ModalOverlay(StackPane root,
                        String title,
                        List<FormField> fields,
                        SaveAction onSave) {
        this(root, title, fields, onSave, null);
    }

    public ModalOverlay(StackPane root,
                        String title,
                        List<FormField> fields,
                        SaveAction onSave,
                        Runnable onClose) {
        this.root    = root;
        this.title   = title;
        this.fields  = fields;
        this.onSave  = onSave;
        this.onClose = onClose;
    }

    public void show() {
        // --- coleta os nodes reais (excluindo overlays anteriores) ---
        var children = root.getChildren();

        // aplica blur ao conteúdo actual
        children.forEach(n -> {
            if (!n.equals(overlay)) n.setEffect(new GaussianBlur(6));
        });

        // fundo semi-transparente
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.45);");
        overlay.setAlignment(Pos.CENTER);

        // --- card ---
        VBox card = buildCard();
        overlay.getChildren().setAll(card);

        // fechar ao clicar fora do card
        overlay.setOnMouseClicked(e -> {
            if (e.getTarget() == overlay) close();
        });

        if (!children.contains(overlay)) {
            children.add(overlay);
        }
    }

    private VBox buildCard() {
        VBox card = new VBox(16);
        card.setPadding(new Insets(28, 32, 28, 32));
        card.setMaxWidth(460);
        card.setStyle(
            "-fx-background-color: " + StyleConstants.BG_DARK + ";" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: " + StyleConstants.ACCENT + ";" +
            "-fx-border-radius: 12;" +
            "-fx-border-width: 1.5;"
        );

        // título
        Label lbTitle = new Label(title);
        lbTitle.setStyle(
            "-fx-font-size: 16px; -fx-font-weight: bold;" +
            "-fx-text-fill: " + StyleConstants.ACCENT + ";"
        );

        // separador
        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: " + StyleConstants.ACCENT + "; -fx-opacity: 0.4;");

        // campos
        Map<String, Control> controls = new LinkedHashMap<>();
        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.getColumnConstraints().addAll(
            colConstraint(140), colConstraint(Double.MAX_VALUE)
        );

        int row = 0;
        for (FormField f : fields) {
            Label lbl = new Label(f.getLabel() + ":");
            lbl.setStyle("-fx-text-fill: " + StyleConstants.TEXT_PRIMARY + "; -fx-font-size: 13px;");

            Control ctrl = buildControl(f);
            controls.put(f.getKey(), ctrl);

            grid.add(lbl,  0, row);
            grid.add(ctrl, 1, row);
            row++;
        }

        // label de erro
        Label lblErr = new Label();
        lblErr.setStyle("-fx-text-fill: #e05252; -fx-font-size: 12px;");
        lblErr.setVisible(false);
        lblErr.setManaged(false);

        // botões
        Button btnSave   = buildButton("Guardar", StyleConstants.ACCENT, "#fff");
        Button btnCancel = buildButton("Cancelar", "#444", StyleConstants.TEXT_PRIMARY);

        btnCancel.setOnAction(e -> close());

        btnSave.setOnAction(e -> {
            Map<String, String> values = new LinkedHashMap<>();
            for (Map.Entry<String, Control> entry : controls.entrySet()) {
                values.put(entry.getKey(), getValue(entry.getValue()));
            }
            try {
                onSave.execute(values);
                close();
            } catch (Exception ex) {
                lblErr.setText("Erro: " + ex.getMessage());
                lblErr.setVisible(true);
                lblErr.setManaged(true);
            }
        });

        HBox buttons = new HBox(12, btnSave, btnCancel);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        card.getChildren().addAll(lbTitle, sep, grid, lblErr, buttons);
        return card;
    }

    // ------------------------------------------------------------------ helpers

    private Control buildControl(FormField f) {
        return switch (f.getType()) {
            case COMBO -> {
                ComboBox<String> cb = new ComboBox<>();
                cb.getItems().addAll(f.getOptions());
                if (!f.getOptions().isEmpty()) cb.setValue(f.getOptions().get(0));
                cb.setMaxWidth(Double.MAX_VALUE);
                styleInput(cb);
                yield cb;
            }
            case PASSWORD -> {
                PasswordField pf = new PasswordField();
                pf.setPromptText(f.getPlaceholder());
                styleInput(pf);
                yield pf;
            }
            case DATE -> {
                DatePicker dp = new DatePicker();
                dp.setPromptText("aaaa-mm-dd");
                dp.setMaxWidth(Double.MAX_VALUE);
                dp.setStyle(inputStyle());
                yield dp;
            }
            default -> {  // TEXT, NUMBER
                TextField tf = new TextField();
                tf.setPromptText(f.getPlaceholder().isBlank() ? f.getLabel() : f.getPlaceholder());
                styleInput(tf);
                yield tf;
            }
        };
    }

    private String getValue(Control ctrl) {
        if (ctrl instanceof TextField tf)       return tf.getText().trim();
        if (ctrl instanceof PasswordField pf)   return pf.getText().trim();
        if (ctrl instanceof ComboBox<?> cb)     return cb.getValue() == null ? "" : cb.getValue().toString();
        if (ctrl instanceof DatePicker dp)      return dp.getValue() == null ? "" : dp.getValue().toString();
        return "";
    }

    private void styleInput(Control c) {
        c.setStyle(inputStyle());
        if (c instanceof Region r) r.setMaxWidth(Double.MAX_VALUE);
    }

    private String inputStyle() {
        return "-fx-background-color: " + StyleConstants.BG_MID + ";" +
               "-fx-text-fill: " + StyleConstants.TEXT_PRIMARY + ";" +
               "-fx-prompt-text-fill: #888;" +
               "-fx-border-color: #555; -fx-border-radius: 6;" +
               "-fx-background-radius: 6; -fx-padding: 6 10;";
    }

    private Button buildButton(String text, String bg, String fg) {
        Button b = new Button(text);
        b.setPrefWidth(110);
        b.setStyle(
            "-fx-background-color: " + bg + ";" +
            "-fx-text-fill: " + fg + ";" +
            "-fx-font-size: 13px; -fx-font-weight: bold;" +
            "-fx-background-radius: 8; -fx-cursor: hand;" +
            "-fx-padding: 8 18;"
        );
        return b;
    }

    private ColumnConstraints colConstraint(double maxW) {
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(maxW == Double.MAX_VALUE ? Priority.ALWAYS : Priority.NEVER);
        if (maxW != Double.MAX_VALUE) cc.setPrefWidth(maxW);
        return cc;
    }

    private void close() {
        root.getChildren().remove(overlay);
        root.getChildren().forEach(n -> n.setEffect(null));
        if (onClose != null) onClose.run();
    }
}
