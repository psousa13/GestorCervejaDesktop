package com.gestorcerveja.ui.screens;

import com.gestorcerveja.controller.LoginController;
import com.gestorcerveja.ui.Main;
import com.gestorcerveja.ui.StyleConstants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

public class LoginScreen {

    private static final LoginController loginController = new LoginController();

    public static HBox build() {
        HBox root = new HBox();
        root.setPrefSize(1280, 760);
        root.setStyle("-fx-background-color: #FAF8F6;");
        root.getChildren().addAll(buildLeft(), buildRight());
        return root;
    }

    // ── Painel esquerdo (branding) ────────────────────────────────────────────

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

    // ── Painel direito (formulário de login) ──────────────────────────────────

    private static VBox buildRight() {
        VBox right = new VBox(0);
        right.setStyle("-fx-background-color: #FAF8F6; -fx-padding: 48px 64px;");
        right.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(right, Priority.ALWAYS);

        Label title = new Label("Bem-vindo de volta");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1A1614;");

        Label sub = new Label("Inicie sessão para aceder ao sistema");
        sub.setStyle("-fx-font-size: 13px; -fx-text-fill: #8C8480;");
        VBox.setMargin(sub, new Insets(5, 0, 36, 0));

        // Campo: Utilizador
        Label userLabel = new Label("UTILIZADOR");
        userLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #8C8480;");
        VBox.setMargin(userLabel, new Insets(0, 0, 6, 0));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Nome de utilizador");
        usernameField.setMaxWidth(400);
        usernameField.setStyle(fieldStyle(false));
        VBox.setMargin(usernameField, new Insets(0, 0, 22, 0));
        usernameField.focusedProperty().addListener((obs, was, is) ->
                usernameField.setStyle(fieldStyle(is)));

        // Campo: Senha
        Label passLabel = new Label("SENHA");
        passLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #8C8480;");
        VBox.setMargin(passLabel, new Insets(0, 0, 6, 0));

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Senha");
        passwordField.setMaxWidth(400);
        passwordField.setStyle(fieldStyle(false));
        VBox.setMargin(passwordField, new Insets(0, 0, 10, 0));
        passwordField.focusedProperty().addListener((obs, was, is) ->
                passwordField.setStyle(fieldStyle(is)));

        // Mensagem de erro
        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: #A32D2D; -fx-font-size: 12px;");
        VBox.setMargin(errorLabel, new Insets(0, 0, 20, 0));

        // Botão
        Button loginBtn = new Button("Entrar no Sistema");
        loginBtn.setStyle(StyleConstants.BTN_PRIMARY + " -fx-font-size: 14px; -fx-padding: 13px 0;");
        loginBtn.setMaxWidth(400);

        // Lógica de autenticação delegada ao LoginController
        Runnable doLogin = () -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                errorLabel.setText("⚠  Por favor preencha todos os campos.");
                return;
            }

            errorLabel.setText("");
            loginBtn.setDisable(true);
            loginBtn.setText("A verificar...");

            try {
                loginController.login(username, password);
                Main.showMain();
            } catch (IllegalArgumentException e) {
                errorLabel.setText("⚠  Credenciais inválidas. Tente novamente.");
                passwordField.clear();
                passwordField.requestFocus();
            } catch (Exception e) {
                errorLabel.setText("⚠  Erro de ligação à base de dados.");
            } finally {
                loginBtn.setDisable(false);
                loginBtn.setText("Entrar no Sistema");
            }
        };

        loginBtn.setOnAction(e -> doLogin.run());
        passwordField.setOnAction(e -> doLogin.run());
        usernameField.setOnAction(e -> passwordField.requestFocus());

        right.getChildren().addAll(
                title, sub,
                userLabel, usernameField,
                passLabel, passwordField,
                errorLabel,
                loginBtn
        );
        return right;
    }

    private static String fieldStyle(boolean focused) {
        String border = focused ? "#7B2D3E" : "#E8E2DF";
        String width  = focused ? "1.5px"   : "1px";
        String shadow = focused ? "-fx-effect: dropshadow(gaussian, rgba(123,45,62,0.15), 4, 0, 0, 0);" : "";
        return "-fx-background-color: white; -fx-border-color: " + border + "; " +
               "-fx-border-width: " + width + "; -fx-border-radius: 8px; " +
               "-fx-background-radius: 8px; -fx-padding: 10px 12px; -fx-font-size: 13px; " + shadow;
    }
}
