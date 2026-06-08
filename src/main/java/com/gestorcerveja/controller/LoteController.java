package com.gestorcerveja.controller;

import com.gestorcerveja.model.Lote;
import com.gestorcerveja.service.LoteService;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
public class LoteController {
    private final LoteService service;
    public LoteController(LoteService service) { this.service = service; }

    public List<Lote> listAll()               { return service.getAll(); }
    public List<Lote> listByPedido(int idp)   { return service.getByPedido(idp); }
    public int create(int idpedido, int idreceita, double litros, LocalDate dataProducao, int idveiculo, int idrequest) { return service.create(idpedido, idreceita, litros, dataProducao, idveiculo, idrequest); }
    public void updateVeiculo(int idlote, int idveiculo) { service.updateVeiculo(idlote, idveiculo); }
    public void delete(int id)                { service.delete(id); }
}
