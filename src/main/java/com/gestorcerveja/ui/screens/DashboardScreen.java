package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.SessionManager;
import com.gestorcerveja.ui.StyleConstants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.sql.SQLException;

public class DashboardScreen {

    public static VBox build() {
        VBox root = new VBox(16);
        root.setPadding(new Insets(18, 22, 18, 22));
        root.setStyle("-fx-background-color: #FAF8F6;");

        root.getChildren().add(buildBanner());
        root.getChildren().add(buildStats());

        String role = SessionManager.getRole();
        if (role.equals("OPERADOR") || role.equals("ARMAZEM")) {
            String msg = role.equals("OPERADOR")
                    ? "⚠  Existem lotes a aguardar registo de etapa pelo seu setor."
                    : "⚠  Existem ingredientes abaixo do stock mínimo.";
            Label alert = new Label(msg);
            alert.setStyle(StyleConstants.ALERT_STRIP);
            alert.setMaxWidth(Double.MAX_VALUE);
            root.getChildren().add(alert);
        }

        root.getChildren().add(buildCards());
        return root;
    }

    private static HBox buildBanner() {
        HBox banner = new HBox();
        banner.setStyle(StyleConstants.BANNER);
        banner.setAlignment(Pos.CENTER_LEFT);

        VBox left = new VBox(4);
        Label greeting = new Label("Bom dia, " + SessionManager.getUserNome() + " 👋");
        greeting.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: white;");
        Label sub = new Label(getBannerSub());
        sub.setStyle("-fx-font-size: 11px; -fx-text-fill: rgba(255,255,255,0.45);");
        left.getChildren().addAll(greeting, sub);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        banner.getChildren().addAll(left, spacer);
        return banner;
    }

    private static HBox buildStats() {
        HBox stats = new HBox(11);
        try {
            String[][] data = getStatsData();
            if (data.length == 0) {
                Label l = new Label("Sem dados de estatísticas para o seu perfil.");
                l.setStyle("-fx-text-fill: #8C8480; -fx-font-size: 12px;");
                stats.getChildren().add(l);
                return stats;
            }
            for (String[] s : data) {
                VBox card = new VBox(6);
                card.setStyle(StyleConstants.STAT_CARD);
                HBox.setHgrow(card, Priority.ALWAYS);
                Label lbl   = new Label(s[0]); lbl.setStyle("-fx-font-size: 10.5px; -fx-text-fill: #8C8480;");
                Label val   = new Label(s[1]); val.setStyle("-fx-font-size: 21px; -fx-font-weight: bold; -fx-text-fill: #1A1614;");
                Label badge = new Label(s[2]); badge.setStyle(StyleConstants.badge(s[3]));
                card.getChildren().addAll(lbl, val, badge);
                stats.getChildren().add(card);
            }
        } catch (SQLException e) {
            Label err = new Label("Erro ao carregar estatísticas: " + e.getMessage());
            err.setStyle("-fx-text-fill: #A32D2D; -fx-font-size: 12px;");
            stats.getChildren().add(err);
        }
        return stats;
    }

    private static HBox buildCards() {
        HBox cards = new HBox(15);
        String[][] items1 = getCard1Items();
        String[][] items2 = getCard2Items();

        VBox card1 = buildListCard(getCard1Title(), items1);
        HBox.setHgrow(card1, Priority.ALWAYS);
        cards.getChildren().add(card1);

        if (items2.length > 0) {
            VBox card2 = buildListCard(getCard2Title(), items2);
            HBox.setHgrow(card2, Priority.ALWAYS);
            cards.getChildren().add(card2);
        }
        return cards;
    }

    private static VBox buildListCard(String title, String[][] items) {
        VBox card = new VBox(0);
        card.setStyle(StyleConstants.LIST_CARD);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 12.5px; -fx-font-weight: bold; -fx-text-fill: #1A1614;");
        VBox.setMargin(titleLabel, new Insets(0, 0, 10, 0));
        card.getChildren().add(titleLabel);

        if (items.length == 0) {
            Label empty = new Label("Sem registos.");
            empty.setStyle("-fx-text-fill: #8C8480; -fx-font-size: 12px; -fx-padding: 8 0;");
            card.getChildren().add(empty);
            return card;
        }

        for (String[] item : items) {
            HBox row = new HBox();
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(8, 0, 8, 0));
            row.setStyle("-fx-border-color: #E8E2DF; -fx-border-width: 0 0 1px 0;");

            VBox left = new VBox(2);
            Label main = new Label(item[0]); main.setStyle("-fx-font-size: 12.5px; -fx-font-weight: bold; -fx-text-fill: #1A1614;");
            Label sub  = new Label(item[1]); sub.setStyle("-fx-font-size: 10.5px; -fx-text-fill: #8C8480;");
            left.getChildren().addAll(main, sub);

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Label badge = new Label(item[2]);
            badge.setStyle(StyleConstants.badge(item[3]));

            row.getChildren().addAll(left, spacer, badge);
            card.getChildren().add(row);
        }
        return card;
    }

    // ── Role data (usa UPPERCASE) ─────────────────────────────────────────────

    private static String getBannerSub() {
        return switch (SessionManager.getRole()) {
            case "ADMIN"     -> "Resumo geral do sistema";
            case "PRODUCAO"  -> "Estado da produção hoje";
            case "OPERADOR"  -> "As suas tarefas de hoje";
            case "QUALIDADE" -> "Lotes a aguardar aprovação";
            case "ARMAZEM"   -> "Estado do stock hoje";
            case "COMERCIAL" -> "Área comercial — resumo do dia";
            default -> "Bem-vindo";
        };
    }

    private static String[][] getStatsData() throws SQLException {
        return switch (SessionManager.getRole()) {
            case "ADMIN" -> new String[][]{
                    {"Pedidos",      String.valueOf(MainScreen.pedidoController.listAll().size()),           "total",        "blue"},
                    {"Lotes",        String.valueOf(MainScreen.loteController.listAll().size()),             "em sistema",   "green"},
                    {"Stock crítico",String.valueOf(MainScreen.ingredienteController.listLowStock().size()), "ingredientes", "wine"},
                    {"Clientes",     String.valueOf(MainScreen.clienteController.listAll().size()),          "registados",   "amber"},
            };
            case "PRODUCAO" -> new String[][]{
                    {"Lotes",        String.valueOf(MainScreen.loteController.listAll().size()),             "em sistema",   "green"},
                    {"Pedidos",      String.valueOf(MainScreen.pedidoController.listAll().size()),           "total",        "blue"},
                    {"Stock crítico",String.valueOf(MainScreen.ingredienteController.listLowStock().size()), "ingredientes", "wine"},
                    {"Receitas",     String.valueOf(MainScreen.receitaController.listAll().size()),          "disponíveis",  "amber"},
            };
            case "ARMAZEM" -> new String[][]{
                    {"Ingredientes", String.valueOf(MainScreen.ingredienteController.listAll().size()),      "total",        "blue"},
                    {"Stock crítico",String.valueOf(MainScreen.ingredienteController.listLowStock().size()), "urgente",      "red"},
            };
            case "COMERCIAL" -> new String[][]{
                    {"Pedidos",      String.valueOf(MainScreen.pedidoController.listAll().size()),           "total",        "blue"},
                    {"Clientes",     String.valueOf(MainScreen.clienteController.listAll().size()),          "registados",   "green"},
            };
            case "OPERADOR", "QUALIDADE" -> new String[][]{
                    {"Lotes",        String.valueOf(MainScreen.loteController.listAll().size()),             "em sistema",   "green"},
            };
            default -> new String[][]{};
        };
    }

    private static String getCard1Title() {
        return switch (SessionManager.getRole()) {
            case "ADMIN", "COMERCIAL"    -> "Pedidos recentes";
            case "PRODUCAO", "OPERADOR"  -> "Lotes recentes";
            case "QUALIDADE"             -> "Aguardam teste";
            case "ARMAZEM"               -> "Ingredientes críticos";
            default -> "Resumo";
        };
    }

    private static String[][] getCard1Items() {
        try {
            return switch (SessionManager.getRole()) {
                case "ADMIN", "COMERCIAL" -> MainScreen.pedidoController.listAll().stream()
                        .limit(5).map(p -> new String[]{
                                "#PED-" + String.format("%03d", p.getId()),
                                "Cliente " + p.getIdcliente() + " · " + p.getDataPedido(),
                                p.getEstado(), "amber"
                        }).toArray(String[][]::new);
                case "PRODUCAO", "OPERADOR" -> MainScreen.loteController.listAll().stream()
                        .limit(5).map(l -> new String[]{
                                "Lote #" + l.getId(),
                                l.getLitros() + "L · Receita " + l.getIdreceita(),
                                "Em produção", "green"
                        }).toArray(String[][]::new);
                case "ARMAZEM" -> MainScreen.ingredienteController.listLowStock().stream()
                        .map(i -> new String[]{
                                i.getNome(),
                                "Stock: " + i.getStockAtual() + " · Mín: " + i.getStockMinimo(),
                                "Crítico", "red"
                        }).toArray(String[][]::new);
                default -> new String[][]{};
            };
        } catch (SQLException e) {
            return new String[][]{{"Erro ao carregar", e.getMessage(), "", "red"}};
        }
    }

    private static String getCard2Title() {
        return switch (SessionManager.getRole()) {
            case "ADMIN"     -> "Lotes recentes";
            case "PRODUCAO"  -> "Receitas disponíveis";
            case "COMERCIAL" -> "Clientes recentes";
            default -> "";
        };
    }

    private static String[][] getCard2Items() {
        try {
            return switch (SessionManager.getRole()) {
                case "ADMIN" -> MainScreen.loteController.listAll().stream()
                        .limit(5).map(l -> new String[]{
                                "Lote #" + l.getId(),
                                l.getLitros() + "L · Receita " + l.getIdreceita(),
                                "Ativo", "green"
                        }).toArray(String[][]::new);
                case "PRODUCAO" -> MainScreen.receitaController.listAll().stream()
                        .limit(5).map(r -> new String[]{
                                r.getNome(),
                                r.getDescricao() != null ? r.getDescricao() : "—",
                                "Disponível", "blue"
                        }).toArray(String[][]::new);
                case "COMERCIAL" -> MainScreen.clienteController.listAll().stream()
                        .limit(5).map(c -> new String[]{
                                c.getEmail() != null ? c.getEmail() : "—",
                                c.getTipoCliente(),
                                "Ativo", "blue"
                        }).toArray(String[][]::new);
                default -> new String[][]{};
            };
        } catch (SQLException e) {
            return new String[][]{{"Erro ao carregar", e.getMessage(), "", "red"}};
        }
    }
}
