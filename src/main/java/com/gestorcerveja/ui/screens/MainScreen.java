package com.gestorcerveja.ui.screens;

import com.gestorcerveja.service.*;
import com.gestorcerveja.ui.components.Sidebar;
import com.gestorcerveja.ui.components.TopBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

public class MainScreen {

    private static TopBar topBar;
    private static StackPane contentArea;

    // Services — shared across all screens
    public static final IngredienteService ingredienteService = new IngredienteService();
    public static final ReceitaService receitaService         = new ReceitaService();
    public static final ClienteService clienteService         = new ClienteService();
    public static final PedidoService pedidoService           = new PedidoService();
    public static final VeiculoService veiculoService         = new VeiculoService();
    public static final LoteService loteService               = new LoteService();
    public static final FaturaService faturaService           = new FaturaService();
    public static final RequestProducaoService requestService = new RequestProducaoService();
    public static final UsuarioService usuarioService         = new UsuarioService();

    public static HBox build() {
        HBox root = new HBox();
        root.setPrefSize(1280, 760);
        root.setStyle("-fx-background-color: #FAF8F6;");

        topBar      = new TopBar("Dashboard");
        contentArea = new StackPane();
        contentArea.setStyle("-fx-background-color: #FAF8F6;");

        ScrollPane scroll = new ScrollPane(contentArea);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: #FAF8F6; -fx-background: #FAF8F6;");
        HBox.setHgrow(scroll, Priority.ALWAYS);

        VBox rightSide = new VBox(topBar, scroll);
        rightSide.setStyle("-fx-background-color: #FAF8F6;");
        HBox.setHgrow(rightSide, Priority.ALWAYS);

        Sidebar sidebar = new Sidebar(MainScreen::navigateTo);
        root.getChildren().addAll(sidebar, rightSide);

        navigateTo("dashboard");
        return root;
    }

    public static void navigateTo(String id) {
        String title = switch (id) {
            case "dashboard"    -> "Dashboard";
            case "pedidos"      -> "Pedidos";
            case "clientes"     -> "Clientes";
            case "faturas"      -> "Faturas";
            case "receitas"     -> "Receitas";
            case "lotes"        -> "Lotes";
            case "producao"     -> "Etapas de Produção";
            case "ingredientes" -> "Ingredientes";
            case "veiculos"     -> "Veículos";
            case "utilizadores" -> "Utilizadores";
            case "qualidade"    -> "Testes de Qualidade";
            case "stock"        -> "Movimentações de Stock";
            default             -> id;
        };
        if (topBar != null) topBar.setTitle(title);

        Region content = switch (id) {
            case "dashboard"    -> DashboardScreen.build();
            case "pedidos"      -> PedidosScreen.build();
            case "clientes"     -> ClientesScreen.build();
            case "faturas"      -> FaturasScreen.build();
            case "receitas"     -> ReceitasScreen.build();
            case "lotes"        -> LotesScreen.build();
            case "producao"     -> ProducaoScreen.build();
            case "ingredientes" -> IngredientesScreen.build();
            case "veiculos"     -> VeiculosScreen.build();
            case "utilizadores" -> UtilizadoresScreen.build();
            case "qualidade"    -> QualidadeScreen.build();
            case "stock"        -> StockScreen.build();
            default             -> new VBox();
        };

        if (contentArea != null) contentArea.getChildren().setAll(content);
    }
}