package com.gestorcerveja.controller;

import com.gestorcerveja.model.RequestProducao;
import com.gestorcerveja.service.RequestProducaoService;

import java.sql.SQLException;
import java.util.List;

public class RequestProducaoController {
    private final RequestProducaoService service = new RequestProducaoService();

    public List<RequestProducao> listAll() throws SQLException {
        return service.getAll();
    }

    /** Cria um request de produção e devolve o id gerado. */
    public int create(int idusuario) throws SQLException {
        return service.create(idusuario);
    }

    public void concluir(int id) throws SQLException {
        service.concluir(id);
    }
}
