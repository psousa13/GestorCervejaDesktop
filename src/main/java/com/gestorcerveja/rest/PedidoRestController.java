package com.gestorcerveja.rest;

import com.gestorcerveja.dto.*;
import com.gestorcerveja.model.Cliente;
import com.gestorcerveja.model.ClienteParticular;
import com.gestorcerveja.model.ClienteRevendedor;
import com.gestorcerveja.model.Pedido;
import com.gestorcerveja.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*")
public class PedidoRestController {

    private final PedidoService  pedidoService;
    private final ClienteService clienteService;

    public PedidoRestController(PedidoService pedidoService,
                                ClienteService clienteService) {
        this.pedidoService  = pedidoService;
        this.clienteService = clienteService;
    }

    /** GET /api/pedidos?email=xxx — lista pedidos filtrados por email quando fornecido */
    @GetMapping
    public List<PedidoResponse> listar(@RequestParam(required = false) String email) {
        List<Pedido> pedidos = (email != null && !email.isBlank())
                ? pedidoService.findByEmail(email)
                : pedidoService.getAll();

        return pedidos.stream().map(p -> {
            PedidoResponse resp = new PedidoResponse(
                    p.getId(), p.getIdcliente(),
                    p.getDataPedido(), p.getEstado(), p.getDataEstimadaConclusao());

            // Populate nested customer info
            try {
                Cliente c = clienteService.getById(p.getIdcliente());
                String nome;
                if ("Revendedor".equalsIgnoreCase(c.getTipoCliente())) {
                    ClienteRevendedor cr = clienteService.getRevendedor(p.getIdcliente());
                    nome = cr.getNomeEmpresa();
                } else {
                    ClienteParticular cp = clienteService.getParticular(p.getIdcliente());
                    nome = cp.getNomeCompleto();
                }
                resp.setCustomer(new PedidoResponse.CustomerInfo(
                        c.getTipoCliente(), nome, c.getEmail(), c.getTelefone()));
            } catch (Exception ignored) {
                // Customer data unavailable — leave null
            }

            return resp;
        }).toList();
    }

    /** POST /api/pedidos — cria um novo pedido a partir da loja */
    @PostMapping
    @Transactional
    public ResponseEntity<Map<String, Object>> criar(@RequestBody PedidoRequest req) {
        CustomerRequest c = req.getCustomer();
        if (c == null || c.getEmail() == null || c.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email do cliente é obrigatório."));
        }

        // 1. Encontra ou cria o cliente (Particular ou Revendedor)
        int idcliente;
        if ("Revendedor".equalsIgnoreCase(c.getType())) {
            idcliente = clienteService.findOrCreateRevendedor(
                    c.getEmail(), c.getPhone(), c.getEmpresa(), c.getNif());
        } else {
            idcliente = clienteService.findOrCreateParticular(
                    c.getEmail(), c.getPhone(), c.getName());
        }

        // 2. Cria o pedido
        int idpedido = pedidoService.create(idcliente, req.getDataEstimadaConclusao());

        // 3. Calcula totais
        double totalValor  = 0;
        int    totalGrades = 0;
        double totalLitros = 0;
        if (req.getItems() != null) {
            for (PedidoItemRequest item : req.getItems()) {
                totalValor  += item.getPrecoUnitario() * item.getQuantidadeGrades();
                totalGrades += item.getQuantidadeGrades();
                totalLitros += item.getQuantidadeLitros();
            }
        }

        return ResponseEntity.ok(Map.of(
                "idpedido",     idpedido,
                "numeroPedido", String.format("PED-%03d", idpedido),
                "estado",       "Pendente",
                "total",        Math.round(totalValor * 100.0) / 100.0,
                "totalGrades",  totalGrades,
                "totalLitros",  totalLitros
        ));
    }
}
