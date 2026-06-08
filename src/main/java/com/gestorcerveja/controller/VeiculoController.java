package com.gestorcerveja.controller;

import com.gestorcerveja.model.Veiculo;
import com.gestorcerveja.service.VeiculoService;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class VeiculoController {
    private final VeiculoService service;
    public VeiculoController(VeiculoService service) { this.service = service; }

    public List<Veiculo> listAll() { return service.getAll(); }
    public void create(String matricula, String marca, String cor, String nome, double capacidade, String tipo) { service.create(matricula, marca, cor, nome, capacidade, tipo); }
    public void update(int id, String nome, String tipo, String marca, String cor, double capacidade) { service.update(id, nome, tipo, marca, cor, capacidade); }
    public void delete(int id) { service.delete(id); }
}
