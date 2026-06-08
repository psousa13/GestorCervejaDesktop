package com.gestorcerveja.controller;

import com.gestorcerveja.model.Receita;
import com.gestorcerveja.service.ReceitaService;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ReceitaController {
    private final ReceitaService service;
    public ReceitaController(ReceitaService service) { this.service = service; }

    public List<Receita> listAll()                              { return service.getAll(); }
    public double getActivePrice(int id)                        { return service.getActivePrice(id); }
    public void create(String nome, String descricao)           { service.create(nome, descricao); }
    public void update(int id, String nome, String descricao)   { service.update(id, nome, descricao); }
    public void setPrice(int idreceita, double preco)           { service.setPrice(idreceita, preco); }
    public void delete(int id)                                  { service.delete(id); }
}
