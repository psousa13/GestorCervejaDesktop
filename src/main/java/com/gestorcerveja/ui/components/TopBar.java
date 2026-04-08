package com.gestorcerveja.ui.components;

import com.gestorcerveja.ui.StyleConstants;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class TopBar extends HBox {

    private final Label titleLabel;
    private final Button btnNew;

    public TopBar(String title) {
        setAlignment(Pos.CENTER_LEFT);
        setStyle(StyleConstants.TOPBAR);

        titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: #1A1614;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnExport = new Button("Exportar");
        btnExport.setStyle(StyleConstants.BTN_SECONDARY);

        btnNew = new Button("+ Novo");
        btnNew.setStyle(StyleConstants.BTN_PRIMARY);

        HBox actions = new HBox(8, btnExport, btnNew);
        actions.setAlignment(Pos.CENTER_RIGHT);

        getChildren().addAll(titleLabel, spacer, actions);
    }

    public void setTitle(String title) { titleLabel.setText(title); }
    public Button getNewButton()       { return btnNew; }
}