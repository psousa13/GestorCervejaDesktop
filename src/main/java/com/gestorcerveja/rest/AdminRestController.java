package com.gestorcerveja.rest;

import com.gestorcerveja.dto.EstadoRequest;
import com.gestorcerveja.dto.PedidoResponse;
import com.gestorcerveja.model.*;
import com.gestorcerveja.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * Endpoints administrativos — protegidos por HTTP Basic Auth.
 * Apenas utilizadores registados na tabela Usuario podem aceder.
 */
@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminRestController {

    private final PedidoService     pedidoService;
    private final ClienteService    clienteService;
    private final UsuarioService    usuarioService;
    private final ReceitaService    receitaService;
    private final IngredienteService ingredienteService;
    private final FaturaService     faturaService;
    private final RoleService       roleService;

    public AdminRestController(PedidoService pedidoService,
                               ClienteService clienteService,
                               UsuarioService usuarioService,
                               ReceitaService receitaService,
                               IngredienteService ingredienteService,
                               FaturaService faturaService,
                               RoleService roleService) {
        this.pedidoService      = pedidoService;
        this.clienteService     = clienteService;
        this.usuarioService     = usuarioService;
        this.receitaService     = receitaService;
        this.ingredienteService = ingredienteService;
        this.faturaService      = faturaService;
        this.roleService        = roleService;
    }

    // ── Dashboard ─────────────────────────────────────────────────────────────

    @GetMapping("/stats")
    public Map<String, Object> stats() {
        long totalPedidos   = pedidoService.getAll().size();
        long totalClientes  = clienteService.getAll().size();
        long totalReceitas  = receitaService.getAll().size();
        long stockCritico   = ingredienteService.getLowStock().size();
        long pedidosPend    = pedidoService.getAll().stream().filter(p -> "Pendente".equals(p.getEstado())).count();
        double totalFaturado = faturaService.getAll().stream().mapToDouble(Fatura::getValorTotal).sum();

        return Map.of(
                "totalPedidos",   totalPedidos,
                "totalClientes",  totalClientes,
                "totalReceitas",  totalReceitas,
                "stockCritico",   stockCritico,
                "pedidosPendentes", pedidosPend,
                "totalFaturado",  Math.round(totalFaturado * 100.0) / 100.0
        );
    }

    // ── Pedidos ───────────────────────────────────────────────────────────────

    @GetMapping("/pedidos")
    public List<PedidoResponse> pedidos() {
        return pedidoService.getAll().stream()
                .map(p -> new PedidoResponse(p.getId(), p.getIdcliente(),
                        p.getDataPedido(), p.getEstado(), p.getDataEstimadaConclusao()))
                .toList();
    }

    @PutMapping("/pedidos/{id}/estado")
    public ResponseEntity<Map<String, String>> updateEstado(
            @PathVariable int id, @RequestBody EstadoRequest req) {
        pedidoService.updateEstado(id, req.getEstado());
        return ResponseEntity.ok(Map.of("estado", req.getEstado()));
    }

    @DeleteMapping("/pedidos/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable int id) {
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ── Clientes ──────────────────────────────────────────────────────────────

    @GetMapping("/clientes")
    public List<Cliente> clientes() { return clienteService.getAll(); }

    // ── Receitas ──────────────────────────────────────────────────────────────

    @GetMapping("/receitas")
    public List<Receita> receitas() { return receitaService.getAll(); }

    // ── Ingredientes (stock crítico) ───────────────────────────────────────────

    @GetMapping("/ingredientes/criticos")
    public List<Ingrediente> stockCritico() { return ingredienteService.getLowStock(); }

    // ── Utilizadores ──────────────────────────────────────────────────────────

    @GetMapping("/utilizadores")
    public List<Usuario> utilizadores() {
        // oculta a senha na resposta
        return usuarioService.getAll().stream().map(u -> {
            u.setSenha("***");
            return u;
        }).toList();
    }

    @PostMapping("/utilizadores")
    public ResponseEntity<Map<String, String>> criarUtilizador(
            @RequestBody Map<String, Object> body) {
        String nome  = (String) body.get("nome");
        String senha = (String) body.get("senha");
        int idrole   = Integer.parseInt(body.get("idrole").toString());
        usuarioService.create(nome, senha, idrole);
        return ResponseEntity.ok(Map.of("message", "Utilizador criado com sucesso."));
    }

    @DeleteMapping("/utilizadores/{id}")
    public ResponseEntity<Void> deleteUtilizador(@PathVariable int id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ── Roles ─────────────────────────────────────────────────────────────────

    @GetMapping("/roles")
    public List<Role> roles() { return roleService.getAll(); }
}
