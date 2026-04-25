package com.gestorcerveja.controller;

import com.gestorcerveja.model.Fatura;
import com.gestorcerveja.model.FaturaItem;
import com.gestorcerveja.service.FaturaService;

import java.sql.SQLException;
import java.util.List;

public class FaturaController {
    private final FaturaService service = new FaturaService();

    public Fatura getByPedido(int idpedido) throws SQLException {
        return service.getByPedido(idpedido);
    }

    public List<FaturaItem> listItems(int idfatura) throws SQLException {
        return service.getItems(idfatura);
    }

    /** Gera uma fatura a partir de um pedido e devolve o id gerado. */
    public int generate(int idpedido) throws SQLException {
        return service.generateFromPedido(idpedido);
    }

    public void updateEstado(int id, String estado) throws SQLException {
        service.updateEstado(id, estado);
    }
}
