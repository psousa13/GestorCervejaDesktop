package com.gestorcerveja.ui.screens;

import com.gestorcerveja.controller.*;
import com.gestorcerveja.ui.components.ScreenBundle;
import com.gestorcerveja.ui.components.Sidebar;
import com.gestorcerveja.ui.components.TopBar;
import com.gestorcerveja.ui.util.ExportUtils;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

public class MainScreen {

    public  static TopBar    topBar;
    /** StackPane exposto para que os modais e a PerfilScreen possam fazer overlay. */
    public  static StackPane contentArea;

    // Controllers — ponto único de acesso para toda a UI
    public static final IngredienteController     ingredienteController  = new IngredienteController();
    public static final ReceitaController         receitaController      = new ReceitaController();
    public static final ClienteController         clienteController      = new ClienteController();
    public static final PedidoController          pedidoController       = new PedidoController();
    public static final VeiculoController         veiculoController      = new VeiculoController();
    public static final LoteController            loteController         = new LoteController();
    public static final FaturaController          faturaController       = new FaturaController();
    public static final RequestProducaoController requestController      = new RequestProducaoController();
    public static final UsuarioController         usuarioController      = new UsuarioController();
    public static final RoleController            roleController         = new RoleController();

    public static HBox build() {
        HBox root = new HBox();
        root.setPrefSize(1280, 760);
        root.setStyle("-fx-background-color: #FAF8F6;");

        topBar      = new TopBar("Dashboard");
        contentArea = new StackPane();
        contentArea.setStyle("-fx-background-color: #FAF8F6;");

        ScrollPane scroll = new ScrollPane(contentArea);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        scroll.setStyle("-fx-background-color: #FAF8F6; -fx-background: #FAF8F6;");
        HBox.setHgrow(scroll, Priority.ALWAYS);

        VBox rightSide = new VBox(topBar, scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);
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
            case "perfil"       -> "O Meu Perfil";
            default             -> id;
        };

        if (topBar != null) topBar.setTitle(title);

        ScreenBundle bundle = switch (id) {
            case "dashboard"    -> ScreenBundle.viewOnly(DashboardScreen.build());
            case "pedidos"      -> PedidosScreen.build();
            case "clientes"     -> ClientesScreen.build();
            case "faturas"      -> FaturasScreen.build();
            case "receitas"     -> ReceitasScreen.build();
            case "lotes"        -> LotesScreen.build();
            case "producao"     -> ProducaoScreen.build();
            case "ingredientes" -> IngredientesScreen.build();
            case "veiculos"     -> VeiculosScreen.build();
            case "utilizadores" -> UtilizadoresScreen.build();
            case "qualidade"    -> ScreenBundle.viewOnly(QualidadeScreen.build());
            case "stock"        -> ScreenBundle.viewOnly(StockScreen.build());
            case "perfil"       -> ScreenBundle.viewOnly(PerfilScreen.build());
            default             -> ScreenBundle.viewOnly(new VBox());
        };

        if (contentArea != null) contentArea.getChildren().setAll(bundle.view());

        // Botões da TopBar
        if (topBar != null) {
            // Perfil: sem botões de ação na topbar
            if ("perfil".equals(id)) {
                topBar.setOnNew(null);
                topBar.setOnExport(null);
                return;
            }

            topBar.setOnNew(bundle.onNew());

            if (bundle.table() != null) {
                final String exportTitle = title;
                topBar.setOnExport(() ->
                    ExportUtils.export(
                        bundle.table(),
                        contentArea.getScene().getWindow(),
                        exportTitle
                    )
                );
            } else {
                topBar.setOnExport(null);
            }
        }
    }
}
