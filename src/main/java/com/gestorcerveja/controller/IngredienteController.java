package com.gestorcerveja.controller;

import com.gestorcerveja.model.Ingrediente;
import com.gestorcerveja.service.IngredienteService;

import java.sql.SQLException;
import java.util.List;

public class IngredienteController {
    private final IngredienteService service = new IngredienteService();

    public List<Ingrediente> listAll()      throws SQLException { return service.getAll(); }
    public List<Ingrediente> listLowStock() throws SQLException { return service.getLowStock(); }

    public void create(String nome, String unidade, double stockAtual, double stockMinimo) throws SQLException {
        service.create(nome, unidade, stockAtual, stockMinimo);
    }

    public void update(int id, String nome, String unidade, double stockAtual, double stockMinimo) throws SQLException {
        service.update(id, nome, unidade, stockAtual, stockMinimo);
    }

    public void updateStock(int id, double novoStock) throws SQLException { service.updateStock(id, novoStock); }
    public void delete(int id)                         throws SQLException { service.delete(id); }
}
