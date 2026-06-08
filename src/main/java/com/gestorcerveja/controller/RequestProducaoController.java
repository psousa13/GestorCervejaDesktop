package com.gestorcerveja.controller;

import com.gestorcerveja.model.RequestProducao;
import com.gestorcerveja.service.RequestProducaoService;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class RequestProducaoController {
    private final RequestProducaoService service;
    public RequestProducaoController(RequestProducaoService service) { this.service = service; }
    public List<RequestProducao> listAll()         { return service.getAll(); }
    public void create(int idusuario)               { service.create(idusuario); }
    public void concluir(int id)                    { service.concluir(id); }
}
