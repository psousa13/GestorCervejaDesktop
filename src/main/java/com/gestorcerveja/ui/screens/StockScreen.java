package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.components.TableBuilder;
import javafx.scene.layout.VBox;

public class StockScreen {
    public static VBox build() {
        return new VBox(TableBuilder.errorLabel("Módulo de movimentações de stock em desenvolvimento."));
    }
}