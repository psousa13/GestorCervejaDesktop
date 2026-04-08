package com.gestorcerveja.ui.components;

import com.gestorcerveja.ui.SessionManager;
import com.gestorcerveja.ui.StyleConstants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Arrays;
import java.util.function.Consumer;

public class Sidebar extends VBox {

    private Button activeBtn = null;
    private final Consumer<String> onNavigate;

    public Sidebar(Consumer<String> onNavigate) {
        this.onNavigate = onNavigate;
        setStyle(StyleConstants.SIDEBAR_BG);
        buildBrand();
        buildNav();
        buildUserPill();
    }

    private void buildBrand() {
        VBox brand = new VBox(3);
        brand.setStyle("-fx-border-color: rgba(255,255,255,0.07); -fx-border-width: 0 0 1px 0; -fx-padding: 18px 18px 14px 18px;");

        Label name = new Label("Cervejaria");
        name.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label role = new Label(SessionManager.getRoleLabel());
        role.setStyle("-fx-font-size: 9px; -fx-text-fill: rgba(255,255,255,0.35);");

        brand.getChildren().addAll(name, role);
        getChildren().add(brand);
    }

    private void buildNav() {
        VBox nav = new VBox(0);
        VBox.setVgrow(nav, Priority.ALWAYS);
        boolean[] first = {true};

        for (String[] group : SessionManager.getNavGroups()) {
            Label sectionLabel = new Label(group[0].toUpperCase());
            sectionLabel.setStyle("-fx-font-size: 8.5px; -fx-text-fill: rgba(255,255,255,0.22); -fx-padding: 14px 12px 3px 12px;");
            nav.getChildren().add(sectionLabel);

            for (int i = 1; i < group.length; i += 2) {
                String id    = group[i];
                String label = group[i + 1];

                Button btn = new Button(label);
                btn.setMaxWidth(Double.MAX_VALUE);
                btn.setStyle(StyleConstants.NAV_NORMAL);
                VBox.setMargin(btn, new Insets(1, 7, 1, 7));

                btn.setOnAction(e -> {
                    setActive(btn);
                    onNavigate.accept(id);
                });

                nav.getChildren().add(btn);
                if (first[0]) { setActive(btn); first[0] = false; }
            }
        }
        getChildren().add(nav);
    }

    private void setActive(Button btn) {
        if (activeBtn != null) activeBtn.setStyle(StyleConstants.NAV_NORMAL);
        activeBtn = btn;
        btn.setStyle(StyleConstants.NAV_ACTIVE);
    }

    private void buildUserPill() {
        String initials = Arrays.stream(SessionManager.getRoleLabel().split(" "))
                .limit(2).map(w -> String.valueOf(w.charAt(0)))
                .reduce("", String::concat).toUpperCase();

        HBox pill = new HBox(9);
        pill.setStyle("-fx-background-color: rgba(255,255,255,0.06); -fx-background-radius: 7px; -fx-padding: 8px 9px;");
        pill.setAlignment(Pos.CENTER_LEFT);

        StackPane avatar = new StackPane();
        Circle circle = new Circle(13, Color.web("#7B2D3E"));
        Label initLabel = new Label(initials);
        initLabel.setStyle("-fx-text-fill: white; -fx-font-size: 10px; -fx-font-weight: bold;");
        avatar.getChildren().addAll(circle, initLabel);

        VBox userInfo = new VBox(2);
        Label nm = new Label(SessionManager.getRoleLabel());
        nm.setStyle("-fx-font-size: 11.5px; -fx-text-fill: rgba(255,255,255,0.75);");
        Label rl = new Label(SessionManager.getRole());
        rl.setStyle("-fx-font-size: 9.5px; -fx-text-fill: rgba(255,255,255,0.3);");
        userInfo.getChildren().addAll(nm, rl);
        pill.getChildren().addAll(avatar, userInfo);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        VBox border = new VBox(pill);
        border.setStyle("-fx-border-color: rgba(255,255,255,0.07); -fx-border-width: 1px 0 0 0; -fx-padding: 10px;");
        getChildren().addAll(spacer, border);
    }
}