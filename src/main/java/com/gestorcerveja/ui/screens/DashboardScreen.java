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

        if (SessionManager.getRole().equals("operador") || SessionManager.getRole().equals("armazem")) {
            String msg = SessionManager.getRole().equals("operador")
                    ? "Existem lotes a aguardar registo de etapa pelo seu setor."
                    : "Existem ingredientes abaixo do stock mínimo.";
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
        Label greeting = new Label("Bom dia, " + SessionManager.getRoleLabel());
        greeting.setStyle("-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: white;");
        Label sub = new Label(getBannerSub());
        sub.setStyle("-fx-font-size: 11px; -fx-text-fill: rgba(255,255,255,0.4);");
        left.getChildren().addAll(greeting, sub);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox quickBtns = new HBox(8);
        quickBtns.setAlignment(Pos.CENTER_RIGHT);
        for (String q : getQuickActions()) {
            Button b = new Button(q);
            b.setStyle(StyleConstants.QUICK_BTN);
            quickBtns.getChildren().add(b);
        }

        banner.getChildren().addAll(left, spacer, quickBtns);
        return banner;
    }

    private static HBox buildStats() {
        HBox stats = new HBox(11);
        try {
            for (String[] s : getStatsData()) {
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
            stats.getChildren().add(new Label("Erro: " + e.getMessage()));
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

    // ── Role data ──

    private static String getBannerSub() {
        return switch (SessionManager.getRole()) {
            case "admin"     -> "Resumo geral do sistema";
            case "producao"  -> "Estado da produção hoje";
            case "operador"  -> "As suas tarefas de hoje";
            case "qualidade" -> "Lotes a aguardar aprovação";
            case "armazem"   -> "Estado do stock hoje";
            case "comercial" -> "Área comercial — resumo do dia";
            default -> "";
        };
    }

    private static String[] getQuickActions() {
        return switch (SessionManager.getRole()) {
            case "admin"     -> new String[]{"Novo Pedido", "Novo Lote", "Novo Cliente"};
            case "producao"  -> new String[]{"Novo Lote", "Ver Pendentes"};
            case "operador"  -> new String[]{"Registar Etapa"};
            case "qualidade" -> new String[]{"Novo Teste", "Ver Pendentes"};
            case "armazem"   -> new String[]{"Entrada de Stock", "Ver Stock Crítico"};
            case "comercial" -> new String[]{"Novo Pedido", "Novo Cliente", "Emitir Fatura"};
            default          -> new String[]{};
        };
    }

    private static String[][] getStatsData() throws SQLException {
        return switch (SessionManager.getRole()) {
            case "admin" -> new String[][]{
                    {"Pedidos",       String.valueOf(MainScreen.pedidoService.getAll().size()),       "total",        "blue"},
                    {"Lotes",         String.valueOf(MainScreen.loteService.getAll().size()),         "em sistema",   "green"},
                    {"Stock crítico", String.valueOf(MainScreen.ingredienteService.getLowStock().size()), "ingredientes","wine"},
                    {"Clientes",      String.valueOf(MainScreen.clienteService.getAll().size()),      "registados",   "amber"},
            };
            case "producao" -> new String[][]{
                    {"Lotes",         String.valueOf(MainScreen.loteService.getAll().size()),         "em sistema",   "green"},
                    {"Pedidos",       String.valueOf(MainScreen.pedidoService.getAll().size()),       "total",        "blue"},
                    {"Stock crítico", String.valueOf(MainScreen.ingredienteService.getLowStock().size()), "ingredientes","wine"},
                    {"Receitas",      String.valueOf(MainScreen.receitaService.getAll().size()),      "disponíveis",  "amber"},
            };
            case "armazem" -> new String[][]{
                    {"Ingredientes",  String.valueOf(MainScreen.ingredienteService.getAll().size()),  "total",        "blue"},
                    {"Stock crítico", String.valueOf(MainScreen.ingredienteService.getLowStock().size()), "urgente",  "red"},
            };
            case "comercial" -> new String[][]{
                    {"Pedidos",       String.valueOf(MainScreen.pedidoService.getAll().size()),       "total",        "blue"},
                    {"Clientes",      String.valueOf(MainScreen.clienteService.getAll().size()),      "registados",   "green"},
            };
            default -> new String[][]{};
        };
    }

    private static String getCard1Title() {
        return switch (SessionManager.getRole()) {
            case "admin","comercial"   -> "Pedidos recentes";
            case "producao","operador" -> "Lotes recentes";
            case "qualidade"           -> "Aguardam teste";
            case "armazem"             -> "Ingredientes críticos";
            default -> "Resumo";
        };
    }

    private static String[][] getCard1Items() {
        try {
            return switch (SessionManager.getRole()) {
                case "admin","comercial" -> MainScreen.pedidoService.getAll().stream()
                        .limit(3).map(p -> new String[]{
                                "#PED-" + String.format("%03d", p.getId()),
                                "Cliente " + p.getIdcliente() + " · " + p.getDataPedido(),
                                p.getEstado(), "amber"
                        }).toArray(String[][]::new);
                case "producao","operador" -> MainScreen.loteService.getAll().stream()
                        .limit(3).map(l -> new String[]{
                                "Lote #" + l.getId(),
                                l.getLitros() + "L · Receita " + l.getIdreceita(),
                                "Em produção", "green"
                        }).toArray(String[][]::new);
                case "armazem" -> MainScreen.ingredienteService.getLowStock().stream()
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
            case "admin"     -> "Lotes recentes";
            case "producao"  -> "Receitas disponíveis";
            case "comercial" -> "Clientes recentes";
            default -> "";
        };
    }

    private static String[][] getCard2Items() {
        try {
            return switch (SessionManager.getRole()) {
                case "admin" -> MainScreen.loteService.getAll().stream()
                        .limit(3).map(l -> new String[]{
                                "Lote #" + l.getId(),
                                l.getLitros() + "L · Receita " + l.getIdreceita(),
                                "Ativo", "green"
                        }).toArray(String[][]::new);
                case "producao" -> MainScreen.receitaService.getAll().stream()
                        .limit(3).map(r -> new String[]{
                                r.getNome(),
                                r.getDescricao() != null ? r.getDescricao() : "—",
                                "Disponível", "blue"
                        }).toArray(String[][]::new);
                case "comercial" -> MainScreen.clienteService.getAll().stream()
                        .limit(3).map(c -> new String[]{
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