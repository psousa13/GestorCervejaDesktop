package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.Main;
import com.gestorcerveja.ui.SessionManager;
import com.gestorcerveja.ui.StyleConstants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

public class LoginScreen {

    private static String selectedRole = "admin";
    private static VBox selectedCard   = null;
    private static Label selectedName  = null;

    public static HBox build() {
        HBox root = new HBox();
        root.setPrefSize(1280, 760);
        root.setStyle("-fx-background-color: #FAF8F6;");
        root.getChildren().addAll(buildLeft(), buildRight());
        return root;
    }

    private static VBox buildLeft() {
        VBox left = new VBox(12);
        left.setStyle("-fx-background-color: #3E1520; -fx-padding: 48px;");
        left.setAlignment(Pos.CENTER);
        left.setPrefWidth(460);
        left.setMinWidth(380);

        Label name = new Label("Cervejaria");
        name.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label tag = new Label("SISTEMA DE GESTÃO");
        tag.setStyle("-fx-font-size: 11px; -fx-text-fill: rgba(255,255,255,0.4);");

        Rectangle divider = new Rectangle(36, 1, Color.web("#FFFFFF", 0.18));

        Label quote = new Label("Qualidade em cada lote,\nprecisão em cada processo.");
        quote.setStyle("-fx-text-fill: rgba(255,255,255,0.3); -fx-font-size: 12px; -fx-font-style: italic;");
        quote.setTextAlignment(TextAlignment.CENTER);
        quote.setAlignment(Pos.CENTER);

        left.getChildren().addAll(name, tag, divider, quote);
        return left;
    }

    private static VBox buildRight() {
        VBox right = new VBox(0);
        right.setStyle("-fx-background-color: #FAF8F6; -fx-padding: 48px 64px;");
        right.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(right, Priority.ALWAYS);

        Label title = new Label("Bem-vindo de volta");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1A1614;");

        Label sub = new Label("Selecione o seu perfil para aceder ao sistema");
        sub.setStyle("-fx-font-size: 13px; -fx-text-fill: #8C8480;");
        VBox.setMargin(sub, new Insets(5, 0, 28, 0));

        Label fieldLabel = new Label("PERFIL DE ACESSO");
        fieldLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #8C8480;");
        VBox.setMargin(fieldLabel, new Insets(0, 0, 10, 0));

        GridPane grid = buildRoleGrid();
        VBox.setMargin(grid, new Insets(0, 0, 24, 0));

        Button loginBtn = new Button("Entrar no Sistema");
        loginBtn.setStyle(StyleConstants.BTN_PRIMARY + " -fx-font-size: 14px; -fx-padding: 13px 0;");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setOnAction(e -> {
            SessionManager.setRole(selectedRole);
            Main.showMain();
        });

        right.getChildren().addAll(title, sub, fieldLabel, grid, loginBtn);
        return right;
    }

    private static GridPane buildRoleGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setMaxWidth(480);

        String[][] roles = {
                {"admin",     "Administrador",        "Acesso total ao sistema"},
                {"producao",  "Gestor de Produção",   "Lotes, receitas e planeamento"},
                {"operador",  "Operador de Produção", "Registo de etapas do processo"},
                {"qualidade", "Gestor de Qualidade",  "Testes e aprovação de lotes"},
                {"armazem",   "Resp. de Armazém",     "Stock e ingredientes"},
                {"comercial", "Comercial",             "Pedidos, clientes e faturas"},
        };

        for (int i = 0; i < roles.length; i++) {
            String roleId   = roles[i][0];
            String roleName = roles[i][1];
            String roleDesc = roles[i][2];

            Label nameLabel = new Label(roleName);
            nameLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #1A1614;");

            Label descLabel = new Label(roleDesc);
            descLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #8C8480;");

            VBox card = new VBox(3, nameLabel, descLabel);
            card.setStyle(StyleConstants.CARD_NORMAL);
            card.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(card, Priority.ALWAYS);

            card.setOnMouseClicked(e -> {
                if (selectedCard != null) {
                    selectedCard.setStyle(StyleConstants.CARD_NORMAL);
                    selectedName.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #1A1614;");
                }
                card.setStyle(StyleConstants.CARD_SELECTED);
                nameLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #7B2D3E;");
                selectedRole = roleId;
                selectedCard = card;
                selectedName = nameLabel;
            });

            grid.add(card, i % 2, i / 2);
        }

        ColumnConstraints col = new ColumnConstraints();
        col.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col, col);
        return grid;
    }
}