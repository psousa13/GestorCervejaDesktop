package com.gestorcerveja.service;

import com.gestorcerveja.model.Veiculo;
import com.gestorcerveja.repository.VeiculoRepository;

import java.sql.SQLException;
import java.util.List;

public class VeiculoService {
    private final VeiculoRepository repo = new VeiculoRepository();

    public List<Veiculo> getAll()      throws SQLException { return repo.findAll(); }
    public Veiculo getById(int id) throws SQLException {
        Veiculo v = repo.findById(id);
        if (v == null) throw new IllegalArgumentException("Veículo " + id + " não encontrado.");
        return v;
    }

    public void create(String matricula, String marca, String cor, String nome,
                       double capacidade, String tipo) throws SQLException {
        if (matricula == null || matricula.isBlank()) throw new IllegalArgumentException("Matrícula é obrigatória.");
        repo.insert(new Veiculo(0, matricula, marca, cor, nome, capacidade, 0, tipo));
    }

    public void update(int id, String nome, String tipo, String marca, String cor, double capacidade) throws SQLException {
        getById(id);
        repo.update(id, nome, tipo, marca, cor, capacidade);
    }

    public void updateOcupacao(int id, double ocupacao) throws SQLException{
        getById(id);
        repo.updateOcupacao(id, ocupacao);
    }

    public void delete(int id) throws SQLException { getById(id); repo.delete(id); }
}
