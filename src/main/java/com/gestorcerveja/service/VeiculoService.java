package com.gestorcerveja.service;

import com.gestorcerveja.model.Veiculo;
import com.gestorcerveja.repository.VeiculoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VeiculoService {
    private final VeiculoRepository repo;
    public VeiculoService(VeiculoRepository repo) { this.repo = repo; }

    public List<Veiculo> getAll()       { return repo.findAll(); }
    public Veiculo getById(int id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Veículo " + id + " não encontrado."));
    }
    public void create(String matricula, String marca, String cor, String nome, double capacidade, String tipo) {
        if (matricula == null || matricula.isBlank()) throw new IllegalArgumentException("Matrícula é obrigatória.");
        repo.insert(new Veiculo(0, matricula, marca, cor, nome, capacidade, 0, tipo));
    }
    public void update(int id, String nome, String tipo, String marca, String cor, double capacidade) {
        getById(id);
        repo.update(id, nome, tipo, marca, cor, capacidade);
    }
    public void addCarga(int id, double litros) {
        Veiculo v = getById(id);
        double novaOcupacao = v.getOcupacaoAtual() + litros;
        if (novaOcupacao > v.getCapacidade()) throw new IllegalArgumentException("Capacidade do veículo excedida.");
        repo.updateOcupacao(id, novaOcupacao);
    }
    public void delete(int id) { getById(id); repo.delete(id); }
}
