package com.gestorcerveja.ui.screens;

import com.gestorcerveja.ui.SessionManager;
import com.gestorcerveja.ui.StyleConstants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DashboardScreen {

    public static VBox build() {
        VBox root = new VBox(16);
        root.setPadding(new Insets(18, 22, 18, 22));
        root.setStyle("-fx-background-color: #FAF8F6;");
        root.getChildren().add(buildBanner());
        root.getChildren().add(buildStats());
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
            String[][] data = getStatsData();
            for (String[] s : data) {
                VBox card = new VBox(6);
                card.setStyle(StyleConstants.STAT_CARD);
                HBox.setHgrow(card, Priority.ALWAYS);
                Label lbl = new Label(s[0]); lbl.setStyle("-fx-font-size: 10.5px; -fx-text-fill: #8C8480;");
                Label val = new Label(s[1]); val.setStyle("-fx-font-size: 21px; -fx-font-weight: bold; -fx-text-fill: #1A1614;");
                Label badge = new Label(s[2]); badge.setStyle(StyleConstants.badge(s[3]));
                card.getChildren().addAll(lbl, val, badge);
                stats.getChildren().add(card);
            }
        } catch (Exception e) {
            stats.getChildren().add(new Label("Erro ao carregar: " + e.getMessage()));
        }
        return stats;
    }

    private static HBox buildCards() {
        HBox cards = new HBox(15);
        try {
            VBox card1 = buildListCard(getCard1Title(), getCard1Items());
            HBox.setHgrow(card1, Priority.ALWAYS);
            cards.getChildren().add(card1);
            String[][] items2 = getCard2Items();
            if (items2.length > 0) {
                VBox card2 = buildListCard(getCard2Title(), items2);
                HBox.setHgrow(card2, Priority.ALWAYS);
                cards.getChildren().add(card2);
            }
        } catch (Exception e) {
            cards.getChildren().add(new Label("Erro: " + e.getMessage()));
        }
        return cards;
    }

    private static VBox buildListCard(String title, String[][] items) {
        VBox card = new VBox(0);
        card.setStyle(StyleConstants.LIST_CARD);
        Label t = new Label(title);
        t.setStyle("-fx-font-size: 12.5px; -fx-font-weight: bold; -fx-text-fill: #1A1614;");
        VBox.setMargin(t, new Insets(0, 0, 10, 0));
        card.getChildren().add(t);
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
            Label sub = new Label(item[1]); sub.setStyle("-fx-font-size: 10.5px; -fx-text-fill: #8C8480;");
            left.getChildren().addAll(main, sub);
            Region sp = new Region(); HBox.setHgrow(sp, Priority.ALWAYS);
            Label badge = new Label(item[2]); badge.setStyle(StyleConstants.badge(item[3]));
            row.getChildren().addAll(left, sp, badge);
            card.getChildren().add(row);
        }
        return card;
    }

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

    private static String[] getQuickActions() {
        return switch (SessionManager.getRole()) {
            case "ADMIN"     -> new String[]{"+ Pedido", "+ Lote", "+ Cliente"};
            case "PRODUCAO"  -> new String[]{"+ Lote", "Ver Pendentes"};
            case "OPERADOR"  -> new String[]{"Registar Etapa"};
            case "QUALIDADE" -> new String[]{"+ Teste", "Ver Pendentes"};
            case "ARMAZEM"   -> new String[]{"Entrada de Stock", "Ver Stock Crítico"};
            case "COMERCIAL" -> new String[]{"+ Pedido", "+ Cliente", "Emitir Fatura"};
            default          -> new String[]{};
        };
    }

    private static String[][] getStatsData() {
        return switch (SessionManager.getRole()) {
            case "ADMIN" -> new String[][]{
                {"Pedidos",       String.valueOf(MainScreen.pedidoController.listAll().size()),           "total",       "blue"},
                {"Lotes",         String.valueOf(MainScreen.loteController.listAll().size()),             "em sistema",  "green"},
                {"Stock crítico", String.valueOf(MainScreen.ingredienteController.listLowStock().size()), "ingredientes","wine"},
                {"Clientes",      String.valueOf(MainScreen.clienteController.listAll().size()),          "registados",  "amber"},
            };
            case "PRODUCAO" -> new String[][]{
                {"Lotes",         String.valueOf(MainScreen.loteController.listAll().size()),             "em sistema",  "green"},
                {"Stock crítico", String.valueOf(MainScreen.ingredienteController.listLowStock().size()), "ingredientes","wine"},
                {"Receitas",      String.valueOf(MainScreen.receitaController.listAll().size()),          "disponíveis", "amber"},
            };
            case "ARMAZEM" -> new String[][]{
                {"Ingredientes",  String.valueOf(MainScreen.ingredienteController.listAll().size()),      "total",       "blue"},
                {"Stock crítico", String.valueOf(MainScreen.ingredienteController.listLowStock().size()), "urgente",     "red"},
            };
            case "COMERCIAL" -> new String[][]{
                {"Pedidos",  String.valueOf(MainScreen.pedidoController.listAll().size()), "total",      "blue"},
                {"Clientes", String.valueOf(MainScreen.clienteController.listAll().size()),"registados", "green"},
            };
            default -> new String[][]{{"Lotes", String.valueOf(MainScreen.loteController.listAll().size()), "em sistema", "green"}};
        };
    }

    private static String getCard1Title() { return switch (SessionManager.getRole()) { case "ADMIN","COMERCIAL" -> "Pedidos recentes"; case "ARMAZEM" -> "Ingredientes críticos"; default -> "Lotes recentes"; }; }
    private static String getCard2Title() { return switch (SessionManager.getRole()) { case "ADMIN" -> "Lotes recentes"; case "PRODUCAO" -> "Receitas disponíveis"; case "COMERCIAL" -> "Clientes recentes"; default -> ""; }; }

    private static String[][] getCard1Items() {
        try {
            return switch (SessionManager.getRole()) {
                case "ADMIN","COMERCIAL" -> MainScreen.pedidoController.listAll().stream().limit(5).map(p -> new String[]{"#PED-"+String.format("%03d",p.getId()), "Cliente "+p.getIdcliente()+" · "+p.getDataPedido(), p.getEstado(), "amber"}).toArray(String[][]::new);
                case "ARMAZEM" -> MainScreen.ingredienteController.listLowStock().stream().map(i -> new String[]{i.getNome(),"Stock: "+i.getStockAtual()+" · Mín: "+i.getStockMinimo(),"Crítico","red"}).toArray(String[][]::new);
                default -> MainScreen.loteController.listAll().stream().limit(5).map(l -> new String[]{"Lote #"+l.getId(), l.getLitros()+"L · Receita "+l.getIdreceita(), "Ativo","green"}).toArray(String[][]::new);
            };
        } catch (Exception e) { return new String[][]{{"Erro", e.getMessage(),"","red"}}; }
    }

    private static String[][] getCard2Items() {
        try {
            return switch (SessionManager.getRole()) {
                case "ADMIN" -> MainScreen.loteController.listAll().stream().limit(5).map(l -> new String[]{"Lote #"+l.getId(), l.getLitros()+"L · Receita "+l.getIdreceita(), "Ativo","green"}).toArray(String[][]::new);
                case "PRODUCAO" -> MainScreen.receitaController.listAll().stream().limit(5).map(r -> new String[]{r.getNome(), r.getDescricao()!=null?r.getDescricao():"—","Disponível","blue"}).toArray(String[][]::new);
                case "COMERCIAL" -> MainScreen.clienteController.listAll().stream().limit(5).map(c -> new String[]{c.getEmail()!=null?c.getEmail():"—", c.getTipoCliente(),"Ativo","blue"}).toArray(String[][]::new);
                default -> new String[][]{};
            };
        } catch (Exception e) { return new String[][]{}; }
    }
}
