package com.gestorcerveja.controller;

import com.gestorcerveja.model.Lote;
import com.gestorcerveja.service.LoteService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class LoteController {
    private final LoteService service = new LoteService();

    public List<Lote> listAll()                        throws SQLException { return service.getAll(); }
    public List<Lote> listByPedido(int idpedido)       throws SQLException { return service.getByPedido(idpedido); }

    public int create(int idpedido, int idreceita, double litros,
                      LocalDate dataProducao, int idveiculo,
                      int idrequestProducao)            throws SQLException {
        return service.create(idpedido, idreceita, litros, dataProducao, idveiculo, idrequestProducao);
    }

    public void updateVeiculo(int idlote, int idveiculo) throws SQLException {
        service.updateVeiculo(idlote, idveiculo);
    }

    public void delete(int id)                          throws SQLException { service.delete(id); }
}
