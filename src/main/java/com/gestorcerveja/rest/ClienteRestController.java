package com.gestorcerveja.rest;

import com.gestorcerveja.model.Cliente;
import com.gestorcerveja.model.ClienteParticular;
import com.gestorcerveja.model.ClienteRevendedor;
import com.gestorcerveja.repository.ClienteRepository;
import com.gestorcerveja.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteRestController {

    private final ClienteService    clienteService;
    private final ClienteRepository clienteRepository;

    public ClienteRestController(ClienteService clienteService,
                                  ClienteRepository clienteRepository) {
        this.clienteService    = clienteService;
        this.clienteRepository = clienteRepository;
    }

    /**
     * POST /api/clientes/register
     * Cria ou devolve um cliente existente (identificação por email).
     */
    @PostMapping("/register")
    @Transactional
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> body) {
        String tipo     = body.getOrDefault("tipoCliente", "Particular");
        String email    = body.get("email");
        String telefone = body.getOrDefault("telefone", "");

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email é obrigatório."));
        }

        int idcliente;

        if ("Revendedor".equalsIgnoreCase(tipo)) {
            String empresa  = body.getOrDefault("empresa", "");
            String vat      = body.getOrDefault("vat", "");
            String contacto = body.getOrDefault("contactoPrincipal", "");
            idcliente = clienteService.findOrCreateRevendedor(email, telefone, empresa, vat);
            if (!empresa.isBlank() || !vat.isBlank() || !contacto.isBlank()) {
                clienteRepository.updateRevendedorDetails(idcliente, empresa, vat, contacto);
            }
        } else {
            String nome = body.getOrDefault("nome", "");
            String nif  = body.getOrDefault("nif", "");
            idcliente = clienteService.findOrCreateParticular(email, telefone, nome);
            if (!nome.isBlank() || !nif.isBlank()) {
                try {
                    clienteRepository.updateParticular(idcliente, email, telefone, nome, nif);
                } catch (Exception ignored) {}
            }
        }

        return ResponseEntity.ok(buildProfile(idcliente));
    }

    /**
     * GET /api/clientes/me?email=xxx
     * Devolve os dados do cliente identificado pelo email.
     */
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(@RequestParam String email) {
        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email é obrigatório."));
        }

        Optional<Cliente> opt = clienteRepository.findByEmail(email);
        if (opt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Cliente não encontrado."));
        }

        return ResponseEntity.ok(buildProfile(opt.get().getId()));
    }

    private Map<String, Object> buildProfile(int idcliente) {
        Cliente c = clienteService.getById(idcliente);
        Map<String, Object> profile = new LinkedHashMap<>();
        profile.put("idcliente",   c.getId());
        profile.put("email",       c.getEmail());
        profile.put("telefone",    c.getTelefone() != null ? c.getTelefone() : "");
        profile.put("tipoCliente", c.getTipoCliente());

        if ("Revendedor".equalsIgnoreCase(c.getTipoCliente())) {
            try {
                ClienteRevendedor cr = clienteService.getRevendedor(idcliente);
                profile.put("nomeEmpresa",       cr.getNomeEmpresa() != null ? cr.getNomeEmpresa() : "");
                profile.put("vatEmpresa",        cr.getVatEmpresa() != null ? cr.getVatEmpresa() : "");
                profile.put("contactoPrincipal", cr.getContactoPrincipal() != null ? cr.getContactoPrincipal() : "");
                profile.put("nome",              cr.getNomeEmpresa());
            } catch (Exception e) {
                profile.put("nomeEmpresa", "");
                profile.put("vatEmpresa", "");
                profile.put("contactoPrincipal", "");
                profile.put("nome", "");
            }
        } else {
            try {
                ClienteParticular cp = clienteService.getParticular(idcliente);
                profile.put("nomeCompleto", cp.getNomeCompleto() != null ? cp.getNomeCompleto() : "");
                profile.put("nif",          cp.getNif() != null ? cp.getNif() : "");
                profile.put("nome",         cp.getNomeCompleto());
            } catch (Exception e) {
                profile.put("nomeCompleto", "");
                profile.put("nif", "");
                profile.put("nome", "");
            }
        }

        return profile;
    }
}
