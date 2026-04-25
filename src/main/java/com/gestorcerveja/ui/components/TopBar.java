package com.gestorcerveja.ui.components;

import com.gestorcerveja.ui.StyleConstants;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class TopBar extends HBox {

    private final Label  titleLabel;
    private final Button btnExport;
    private final Button btnNew;

    public TopBar(String title) {
        setAlignment(Pos.CENTER_LEFT);
        setStyle(StyleConstants.TOPBAR);

        titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: #1A1614;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        btnExport = new Button("\u2193 Exportar");
        btnExport.setStyle(StyleConstants.BTN_SECONDARY);

        btnNew = new Button("+ Novo");
        btnNew.setStyle(StyleConstants.BTN_PRIMARY);

        HBox actions = new HBox(8, btnExport, btnNew);
        actions.setAlignment(Pos.CENTER_RIGHT);

        getChildren().addAll(titleLabel, spacer, actions);
        applyVisibility("Dashboard");
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
        applyVisibility(title);
    }

    /** Handler do botao + Novo. null = ocultar botao. */
    public void setOnNew(Runnable action) {
        if (action == null) { btnNew.setVisible(false);   btnNew.setManaged(false);   return; }
        btnNew.setVisible(true);  btnNew.setManaged(true);
        btnNew.setOnAction(e -> action.run());
    }

    /** Handler do botao Exportar. null = ocultar botao. */
    public void setOnExport(Runnable action) {
        if (action == null) { btnExport.setVisible(false); btnExport.setManaged(false); return; }
        btnExport.setVisible(true); btnExport.setManaged(true);
        btnExport.setOnAction(e -> action.run());
    }

    private void applyVisibility(String title) {
        boolean isDash = "Dashboard".equals(title);
        btnExport.setVisible(!isDash); btnExport.setManaged(!isDash);
        btnNew.setVisible(!isDash);    btnNew.setManaged(!isDash);
        btnExport.setOnAction(null);
        btnNew.setOnAction(null);
    }
}
