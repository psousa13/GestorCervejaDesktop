package com.gestorcerveja.ui.screens;

import com.gestorcerveja.SpringContext;
import com.gestorcerveja.controller.*;
import com.gestorcerveja.ui.components.ScreenBundle;
import com.gestorcerveja.ui.components.Sidebar;
import com.gestorcerveja.ui.components.TopBar;
import com.gestorcerveja.ui.util.ExportUtils;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

/**
 * Screen principal da aplicação.
 *
 * Os controllers de negócio são obtidos do contexto Spring via
 * {@link SpringContext#getBean(Class)} — garantindo que são
 * os mesmos singletons geridos pelo Spring (com todas as
 * dependências injetadas).
 */
public class MainScreen {

    public static TopBar    topBar;
    public static StackPane contentArea;

    // ── Beans Spring — carregados uma vez em build() ──────────────────────────
    public static IngredienteController     ingredienteController;
    public static ReceitaController         receitaController;
    public static ClienteController         clienteController;
    public static PedidoController          pedidoController;
    public static VeiculoController         veiculoController;
    public static LoteController            loteController;
    public static FaturaController          faturaController;
    public static RequestProducaoController requestController;
    public static UsuarioController         usuarioController;
    public static RoleController            roleController;

    public static HBox build() {
        // Obtém todos os beans do contexto Spring
        ingredienteController  = SpringContext.getBean(IngredienteController.class);
        receitaController      = SpringContext.getBean(ReceitaController.class);
        clienteController      = SpringContext.getBean(ClienteController.class);
        pedidoController       = SpringContext.getBean(PedidoController.class);
        veiculoController      = SpringContext.getBean(VeiculoController.class);
        loteController         = SpringContext.getBean(LoteController.class);
        faturaController       = SpringContext.getBean(FaturaController.class);
        requestController      = SpringContext.getBean(RequestProducaoController.class);
        usuarioController      = SpringContext.getBean(UsuarioController.class);
        roleController         = SpringContext.getBean(RoleController.class);

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

        if (topBar != null) {
            if ("perfil".equals(id)) { topBar.setOnNew(null); topBar.setOnExport(null); return; }
            topBar.setOnNew(bundle.onNew());
            if (bundle.table() != null) {
                final String t = title;
                topBar.setOnExport(() -> ExportUtils.export(bundle.table(), contentArea.getScene().getWindow(), t));
            } else {
                topBar.setOnExport(null);
            }
        }
    }
}
