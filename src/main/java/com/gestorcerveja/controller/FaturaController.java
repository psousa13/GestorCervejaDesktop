package com.gestorcerveja.controller;

import com.gestorcerveja.model.Fatura;
import com.gestorcerveja.service.FaturaService;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class FaturaController {
    private final FaturaService service;
    public FaturaController(FaturaService service) { this.service = service; }
    public List<Fatura> listAll()          { return service.getAll(); }
    public Fatura getByPedido(int idp)     { return service.getByPedido(idp); }
}
