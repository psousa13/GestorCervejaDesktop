package com.gestorcerveja.service;

import com.gestorcerveja.model.Receita;
import com.gestorcerveja.model.ReceitaPreco;
import com.gestorcerveja.repository.ReceitaPrecoRepository;
import com.gestorcerveja.repository.ReceitaRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ReceitaService {
    private final ReceitaRepository repo          = new ReceitaRepository();
    private final ReceitaPrecoRepository precoRepo = new ReceitaPrecoRepository();

    public List<Receita> getAll() throws SQLException {
        return repo.findAll();
    }

    public Receita getById(int id) throws SQLException {
        Receita r = repo.findById(id);
        if (r == null) throw new IllegalArgumentException("Receita " + id + " not found.");
        return r;
    }

    public void create(String nome, String descricao) throws SQLException {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome is required.");
        repo.insert(new Receita(0, nome, descricao));
    }

    public void update(int id, String nome, String descricao) throws SQLException {
        getById(id);
        repo.update(new Receita(id, nome, descricao));
    }

    public void delete(int id) throws SQLException {
        getById(id);
        repo.delete(id);
    }

    public void setPrice(int idreceita, double precoPorLitro) throws SQLException {
        if (precoPorLitro <= 0) throw new IllegalArgumentException("Price must be positive.");
        getById(idreceita);
        precoRepo.insert(new ReceitaPreco(0, idreceita, precoPorLitro, LocalDate.now()));
    }

    public double getActivePrice(int idreceita) throws SQLException {
        ReceitaPreco rp = precoRepo.findActivePrice(idreceita);
        if (rp == null) throw new IllegalStateException("No price set for receita " + idreceita);
        return rp.getPrecoPorLitro();
    }
}