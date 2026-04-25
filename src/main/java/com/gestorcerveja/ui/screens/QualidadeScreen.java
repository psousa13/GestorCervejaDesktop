package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.components.*;
import com.gestorcerveja.ui.components.TableBuilder;
import javafx.scene.layout.VBox;

public class QualidadeScreen {
    public static VBox build() {
        return new VBox(TableBuilder.errorLabel("Módulo de qualidade em desenvolvimento."));
    }
}
