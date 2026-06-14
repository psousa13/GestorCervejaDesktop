package com.gestorcerveja.rest;

import com.gestorcerveja.dto.ReceitaDTO;
import com.gestorcerveja.service.ReceitaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/receitas")
@CrossOrigin(origins = "*")
public class ReceitaRestController {

    private final ReceitaService receitaService;

    public ReceitaRestController(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }
    
    @GetMapping
    public List<ReceitaDTO> listar() {
        return receitaService.getAll().stream()
                .map(r -> {
                    // Captura o preço como Objeto (pode ser null)
                    Double precoAtivo = receitaService.getActivePrice(r.getId());
                    
                    // Se for null, vira 0.0, evitando o erro de unboxing
                    double precoValido = (precoAtivo != null) ? precoAtivo : 0.0;

                    return new ReceitaDTO(
                            r.getId(),
                            r.getNome(),
                            r.getDescricao(),
                            precoValido
                    );
                })
                .toList();
    }
    
    /** GET /api/receitas/{id} */
    @GetMapping("/{id}")
    public ReceitaDTO detalhe(@PathVariable int id) {
        var r = receitaService.getById(id);
        return new ReceitaDTO(r.getId(), r.getNome(), r.getDescricao(),
                receitaService.getActivePrice(id));
    }
}
