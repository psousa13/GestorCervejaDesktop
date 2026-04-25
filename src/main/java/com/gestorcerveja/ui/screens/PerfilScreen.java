package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.Main;
import com.gestorcerveja.ui.SessionManager;
import com.gestorcerveja.ui.StyleConstants;
import com.gestorcerveja.ui.components.FormField;
import com.gestorcerveja.ui.components.ModalOverlay;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Arrays;
import java.util.List;

public class PerfilScreen {

    public static VBox build() {
        VBox root = new VBox(0);
        root.setStyle(StyleConstants.CONTENT_BG);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(40, 40, 40, 40));

        // ── Card central ─────────────────────────────────────────────────────
        VBox card = new VBox(0);
        card.setMaxWidth(560);
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #E8E2DF; -fx-border-width: 1px;" +
            "-fx-border-radius: 14px; -fx-background-radius: 14px;"
        );

        card.getChildren().addAll(
            buildHeader(),
            buildDivider(),
            buildInfoSection(),
            buildDivider(),
            buildActionsSection()
        );

        root.getChildren().add(card);
        return root;
    }

    // ── Cabeçalho: avatar + nome + role ──────────────────────────────────────

    private static VBox buildHeader() {
        VBox header = new VBox(12);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(36, 32, 28, 32));

        // Avatar com iniciais
        StackPane avatar = buildAvatar(56);

        // Nome e role
        String nome = SessionManager.getUserNome();
        Label lbNome = new Label(nome);
        lbNome.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #1A1614;");

        Label lbRole = new Label(SessionManager.getRoleLabel());
        lbRole.setStyle(
            "-fx-background-color: #F5EEF0; -fx-text-fill: #7B2D3E;" +
            "-fx-background-radius: 20px; -fx-padding: 3px 12px; -fx-font-size: 11px;"
        );

        header.getChildren().addAll(avatar, lbNome, lbRole);
        return header;
    }

    // ── Secção de informação ─────────────────────────────────────────────────

    private static VBox buildInfoSection() {
        VBox section = new VBox(0);
        section.setPadding(new Insets(20, 32, 20, 32));

        Label title = new Label("INFORMAÇÃO DA CONTA");
        title.setStyle("-fx-font-size: 9.5px; -fx-font-weight: bold; -fx-text-fill: #8C8480;");
        VBox.setMargin(title, new Insets(0, 0, 14, 0));
        section.getChildren().add(title);

        var user = SessionManager.getUser();
        String id   = user != null ? String.valueOf(user.getId())     : "—";
        String nome = user != null ? user.getNome()                   : SessionManager.getUserNome();
        String role = SessionManager.getRoleLabel();

        section.getChildren().addAll(
            infoRow("Utilizador", nome,  "👤"),
            infoRow("Role",       role,  "🔑"),
            infoRow("ID Interno", "#" + id, "🆔")
        );
        return section;
    }

    private static HBox infoRow(String label, String value, String icon) {
        HBox row = new HBox(12);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(11, 0, 11, 0));
        row.setStyle("-fx-border-color: #F0EBE8; -fx-border-width: 0 0 1px 0;");

        Label ico = new Label(icon);
        ico.setMinWidth(22);
        ico.setStyle("-fx-font-size: 14px;");

        VBox info = new VBox(2);
        Label lbl = new Label(label);
        lbl.setStyle("-fx-font-size: 10px; -fx-text-fill: #8C8480;");
        Label val = new Label(value);
        val.setStyle("-fx-font-size: 13px; -fx-text-fill: #1A1614; -fx-font-weight: bold;");
        info.getChildren().addAll(lbl, val);

        row.getChildren().addAll(ico, info);
        return row;
    }

    // ── Secção de ações ──────────────────────────────────────────────────────

    private static VBox buildActionsSection() {
        VBox section = new VBox(10);
        section.setPadding(new Insets(24, 32, 28, 32));

        Label title = new Label("AÇÕES");
        title.setStyle("-fx-font-size: 9.5px; -fx-font-weight: bold; -fx-text-fill: #8C8480;");
        VBox.setMargin(title, new Insets(0, 0, 4, 0));

        Button btnEdit    = buildButton("✏  Editar Perfil",       StyleConstants.BTN_PRIMARY,    false);
        Button btnLogout  = buildButton("→  Terminar Sessão",     logoutStyle(),                 false);

        btnEdit.setOnAction(e -> showEditModal());
        btnLogout.setOnAction(e -> doLogout());

        section.getChildren().addAll(title, btnEdit, btnLogout);
        return section;
    }

    // ── Modal de edição ──────────────────────────────────────────────────────

    private static void showEditModal() {
        var user = SessionManager.getUser();
        if (user == null) return;

        List<FormField> fields = List.of(
            new FormField("nome",         "Nome de utilizador",
                          FormField.Type.TEXT, List.of(), user.getNome()),
            new FormField("novaSenha",    "Nova senha",
                          FormField.Type.PASSWORD, List.of(), "Deixar vazio = não alterar"),
            new FormField("confirmarSenha", "Confirmar senha",
                          FormField.Type.PASSWORD, List.of(), "Repetir nova senha")
        );

        new ModalOverlay(
            MainScreen.contentArea,
            "Editar Perfil",
            fields,
            values -> {
                String novoNome  = values.get("nome");
                String novaSenha = values.get("novaSenha");
                String confirmar = values.get("confirmarSenha");

                if (novoNome.isBlank())
                    throw new IllegalArgumentException("O nome não pode estar vazio.");
                if (!novaSenha.isBlank() && !novaSenha.equals(confirmar))
                    throw new IllegalArgumentException("As senhas não coincidem.");

                MainScreen.usuarioController.updateSelf(user.getId(), novoNome, novaSenha);

                // Atualiza sessão em memória
                user.setNome(novoNome);
                if (!novaSenha.isBlank()) user.setSenha(novaSenha);
                SessionManager.setUser(user);

                // Recarrega o ecrã para reflectir o novo nome
                MainScreen.navigateTo("perfil");
            },
            () -> MainScreen.navigateTo("perfil")
        ).show();
    }

    // ── Logout ───────────────────────────────────────────────────────────────

    private static void doLogout() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Terminar Sessão");
        confirm.setHeaderText(null);
        confirm.setContentText("Tem a certeza que deseja terminar sessão?");
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                SessionManager.clear();
                Main.showLogin();
            }
        });
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    public static StackPane buildAvatar(double radius) {
        String initials = Arrays.stream(SessionManager.getUserNome().split(" "))
                .limit(2)
                .map(w -> w.isBlank() ? "" : String.valueOf(w.charAt(0)))
                .reduce("", String::concat)
                .toUpperCase();
        if (initials.isBlank()) initials = "U";

        Circle circle = new Circle(radius, Color.web("#7B2D3E"));

        // Anel externo
        Circle ring = new Circle(radius + 4);
        ring.setFill(Color.TRANSPARENT);
        ring.setStroke(Color.web("#F5EEF0"));
        ring.setStrokeWidth(2);

        Label lb = new Label(initials);
        lb.setStyle("-fx-text-fill: white; -fx-font-size: " + (radius * 0.55) + "px; -fx-font-weight: bold;");

        StackPane sp = new StackPane(ring, circle, lb);
        sp.setMaxSize((radius + 4) * 2, (radius + 4) * 2);
        return sp;
    }

    private static Separator buildDivider() {
        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #F0EBE8;");
        return sep;
    }

    private static Button buildButton(String text, String style, boolean outline) {
        Button b = new Button(text);
        b.setMaxWidth(Double.MAX_VALUE);
        b.setStyle(style);
        return b;
    }

    private static String logoutStyle() {
        return "-fx-background-color: white;" +
               "-fx-text-fill: #A32D2D;" +
               "-fx-border-color: #F5CECE; -fx-border-width: 1px; -fx-border-radius: 7px;" +
               "-fx-background-radius: 7px; -fx-font-size: 12px; -fx-font-weight: bold;" +
               "-fx-padding: 8px 13px; -fx-cursor: hand;";
    }
}
