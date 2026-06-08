package com.gestorcerveja.service;

import com.gestorcerveja.model.Receita;
import com.gestorcerveja.repository.ReceitaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReceitaService {
    private final ReceitaRepository repo;
    public ReceitaService(ReceitaRepository repo) { this.repo = repo; }

    public List<Receita> getAll() { return repo.findAll(); }
    public Receita getById(int id) { return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Receita " + id + " não encontrada.")); }
    public double getActivePrice(int idreceita) {
        return repo.findActivePrice(idreceita).map(p -> p.getPrecoPorLitro())
                .orElseThrow(() -> new IllegalArgumentException("Sem preço ativo para receita " + idreceita));
    }
    public void create(String nome, String descricao) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
        repo.insert(nome, descricao);
    }
    public void update(int id, String nome, String descricao) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
        getById(id);
        repo.update(id, nome, descricao);
    }
    public void setPrice(int idreceita, double preco) { getById(idreceita); repo.setPrice(idreceita, preco); }
    public void delete(int id) { getById(id); repo.delete(id); }
}
