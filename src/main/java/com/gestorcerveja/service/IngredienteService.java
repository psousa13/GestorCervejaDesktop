package com.gestorcerveja.service;

import com.gestorcerveja.model.Ingrediente;
import com.gestorcerveja.repository.IngredienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IngredienteService {
    private final IngredienteRepository repo;
    public IngredienteService(IngredienteRepository repo) { this.repo = repo; }

    public List<Ingrediente> getAll()      { return repo.findAll(); }
    public List<Ingrediente> getLowStock() { return repo.findBelowMinStock(); }

    public Ingrediente getById(int id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Ingrediente " + id + " não encontrado."));
    }
    public void create(String nome, String unidade, double stockAtual, double stockMinimo) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
        repo.insert(new Ingrediente(0, nome, unidade, stockAtual, stockMinimo));
    }
    public void update(int id, String nome, String unidade, double stockAtual, double stockMinimo) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
        getById(id);
        repo.update(id, nome, unidade, stockAtual, stockMinimo);
    }
    public void updateStock(int id, double novoStock) {
        if (novoStock < 0) throw new IllegalArgumentException("Stock não pode ser negativo.");
        getById(id);
        repo.updateStock(id, novoStock);
    }
    public void delete(int id) { getById(id); repo.delete(id); }
}
