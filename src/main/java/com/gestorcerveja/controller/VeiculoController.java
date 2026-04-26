package com.gestorcerveja.controller;

import com.gestorcerveja.model.Veiculo;
import com.gestorcerveja.service.VeiculoService;

import java.sql.SQLException;
import java.util.List;

public class VeiculoController {
    private final VeiculoService service = new VeiculoService();

    public List<Veiculo> listAll() throws SQLException { return service.getAll(); }

    public void create(String matricula, String marca, String cor, String nome,
                       double capacidade, String tipo) throws SQLException {
        service.create(matricula, marca, cor, nome, capacidade, tipo);
    }

    public void update(int id, String nome, String tipo, String marca, String cor, double capacidade) throws SQLException {
        service.update(id, nome, tipo, marca, cor, capacidade);
    }

    public void delete(int id) throws SQLException { service.delete(id); }
}
