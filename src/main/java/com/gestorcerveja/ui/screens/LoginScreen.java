package com.gestorcerveja.ui.screens;

import com.gestorcerveja.SpringContext;
import com.gestorcerveja.controller.LoginController;
import com.gestorcerveja.model.Usuario;
import com.gestorcerveja.ui.JavaFXApplication;
import com.gestorcerveja.ui.SessionManager;
import com.gestorcerveja.ui.StyleConstants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LoginScreen {

    public static VBox build() {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #FAF8F6;");
        root.setPrefSize(1280, 760);

        VBox card = new VBox(18);
        card.setMaxWidth(380);
        card.setAlignment(Pos.CENTER);
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #E8E2DF; -fx-border-width: 1px;" +
            "-fx-border-radius: 14px; -fx-background-radius: 14px;" +
            "-fx-padding: 40px 36px;"
        );

        // Branding
        Label brand = new Label("🍺 Gestor Cerveja");
        brand.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #3E1520;");
        Label sub = new Label("Aceda à sua conta para continuar");
        sub.setStyle("-fx-font-size: 12px; -fx-text-fill: #8C8480;");

        // Campos
        TextField tfNome = new TextField();
        tfNome.setPromptText("Nome de utilizador");
        tfNome.setStyle(inputStyle());

        PasswordField pfSenha = new PasswordField();
        pfSenha.setPromptText("Senha");
        pfSenha.setStyle(inputStyle());

        Label lblErro = new Label();
        lblErro.setStyle("-fx-text-fill: #A32D2D; -fx-font-size: 11px;");
        lblErro.setVisible(false);

        Button btnLogin = new Button("Entrar");
        btnLogin.setMaxWidth(Double.MAX_VALUE);
        btnLogin.setStyle(StyleConstants.BTN_PRIMARY + "-fx-font-size: 14px; -fx-padding: 10px;");

        Runnable doLogin = () -> {
            String nome  = tfNome.getText().trim();
            String senha = pfSenha.getText().trim();
            if (nome.isBlank() || senha.isBlank()) {
                lblErro.setText("Preencha todos os campos.");
                lblErro.setVisible(true);
                return;
            }
            try {
                // Bean Spring via SpringContext
                LoginController loginCtrl = SpringContext.getBean(LoginController.class);
                Usuario user = loginCtrl.login(nome, senha);
                // Obter nome do role
                String roleNome = user.getIdrole() == 1 ? "ADMIN" : "OPERADOR"; // simplificado
                try {
                    var roles = SpringContext.getBean(com.gestorcerveja.controller.RoleController.class).listAll();
                    roleNome = roles.stream()
                        .filter(r -> r.getId() == user.getIdrole())
                        .map(r -> r.getNome().toUpperCase())
                        .findFirst().orElse("ADMIN");
                } catch (Exception ignored) {}

                SessionManager.setUser(user);
                SessionManager.setRole(roleNome);
                JavaFXApplication.showMain();
            } catch (Exception e) {
                lblErro.setText("Credenciais inválidas.");
                lblErro.setVisible(true);
            }
        };

        btnLogin.setOnAction(e -> doLogin.run());
        pfSenha.setOnAction(e -> doLogin.run());

        card.getChildren().addAll(brand, sub, new Separator(), tfNome, pfSenha, lblErro, btnLogin);
        root.getChildren().add(card);
        return root;
    }

    private static String inputStyle() {
        return "-fx-background-color: #F5F2F0;" +
               "-fx-border-color: #E0D9D6; -fx-border-width: 1px; -fx-border-radius: 8px;" +
               "-fx-background-radius: 8px; -fx-padding: 9px 12px; -fx-font-size: 13px;";
    }
}
