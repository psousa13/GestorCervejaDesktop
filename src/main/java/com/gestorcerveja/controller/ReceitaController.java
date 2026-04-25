package com.gestorcerveja.controller;

import com.gestorcerveja.model.Receita;
import com.gestorcerveja.service.ReceitaService;

import java.sql.SQLException;
import java.util.List;

public class ReceitaController {
    private final ReceitaService service = new ReceitaService();

    public List<Receita> listAll() throws SQLException {
        return service.getAll();
    }

    public void create(String nome, String descricao) throws SQLException {
        service.create(nome, descricao);
    }

    public void update(int id, String nome, String descricao) throws SQLException {
        service.update(id, nome, descricao);
    }

    public void setPrice(int idreceita, double preco) throws SQLException {
        service.setPrice(idreceita, preco);
    }

    /** Devolve o preço ativo em €/L, ou lança {@link IllegalStateException} se não existir. */
    public double getActivePrice(int idreceita) throws SQLException {
        return service.getActivePrice(idreceita);
    }

    public void delete(int id) throws SQLException {
        service.delete(id);
    }
}
