package com.gestorcerveja.service;

import com.gestorcerveja.model.Veiculo;
import com.gestorcerveja.repository.VeiculoRepository;

import java.sql.SQLException;
import java.util.List;

public class VeiculoService {
    private final VeiculoRepository repo = new VeiculoRepository();

    public List<Veiculo> getAll() throws SQLException {
        return repo.findAll();
    }

    public Veiculo getById(int id) throws SQLException {
        Veiculo v = repo.findById(id);
        if (v == null) throw new IllegalArgumentException("Veiculo " + id + " not found.");
        return v;
    }

    public void create(String matricula, String marca, String cor, String nome,
                       double capacidade, String tipo) throws SQLException {
        if (matricula == null || matricula.isBlank()) throw new IllegalArgumentException("Matricula is required.");
        if (!tipo.equals("Camião") && !tipo.equals("Carrinha")) throw new IllegalArgumentException("Tipo must be 'Camião' or 'Carrinha'.");
        repo.insert(new Veiculo(0, matricula, marca, cor, nome, capacidade, 0, tipo));
    }

    public void addCarga(int id, double litros) throws SQLException {
        Veiculo v = getById(id);
        double novaOcupacao = v.getOcupacaoAtual() + litros;
        if (novaOcupacao > v.getCapacidade())
            throw new IllegalStateException("Capacity exceeded. Available: " + (v.getCapacidade() - v.getOcupacaoAtual()) + "L");
        repo.updateOcupacao(id, novaOcupacao);
    }

    public void clearCarga(int id) throws SQLException {
        getById(id);
        repo.updateOcupacao(id, 0);
    }

    public void delete(int id) throws SQLException {
        getById(id);
        repo.delete(id);
    }
}