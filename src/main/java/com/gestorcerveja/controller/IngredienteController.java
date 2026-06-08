package com.gestorcerveja.controller;

import com.gestorcerveja.model.Ingrediente;
import com.gestorcerveja.service.IngredienteService;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class IngredienteController {
    private final IngredienteService service;
    public IngredienteController(IngredienteService service) { this.service = service; }

    public List<Ingrediente> listAll()      { return service.getAll(); }
    public List<Ingrediente> listLowStock() { return service.getLowStock(); }
    public void create(String nome, String unidade, double stockAtual, double stockMinimo) { service.create(nome, unidade, stockAtual, stockMinimo); }
    public void update(int id, String nome, String unidade, double sa, double sm)          { service.update(id, nome, unidade, sa, sm); }
    public void updateStock(int id, double novoStock)                                      { service.updateStock(id, novoStock); }
    public void delete(int id)                                                             { service.delete(id); }
}
