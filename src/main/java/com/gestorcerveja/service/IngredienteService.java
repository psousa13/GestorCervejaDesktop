package com.gestorcerveja.service;

import com.gestorcerveja.model.Ingrediente;
import com.gestorcerveja.repository.IngredienteRepository;

import java.sql.SQLException;
import java.util.List;

public class IngredienteService {
    private final IngredienteRepository repo = new IngredienteRepository();

    public List<Ingrediente> getAll() throws SQLException {
        return repo.findAll();
    }

    public Ingrediente getById(int id) throws SQLException {
        Ingrediente i = repo.findById(id);
        if (i == null) throw new IllegalArgumentException("Ingrediente " + id + " not found.");
        return i;
    }

    public List<Ingrediente> getLowStock() throws SQLException {
        return repo.findBelowMinStock();
    }

    public void create(String nome, String unidade, double stockAtual, double stockMinimo) throws SQLException {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome is required.");
        repo.insert(new Ingrediente(0, nome, unidade, stockAtual, stockMinimo));
    }

    public void updateStock(int id, double novoStock) throws SQLException {
        if (novoStock < 0) throw new IllegalArgumentException("Stock cannot be negative.");
        getById(id);
        repo.updateStock(id, novoStock);
    }

    public void delete(int id) throws SQLException {
        getById(id);
        repo.delete(id);
    }
}