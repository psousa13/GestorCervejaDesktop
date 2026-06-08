package com.gestorcerveja.controller;

import com.gestorcerveja.model.Pedido;
import com.gestorcerveja.service.PedidoService;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
public class PedidoController {
    private final PedidoService service;
    public PedidoController(PedidoService service) { this.service = service; }

    public List<Pedido> listAll()                               { return service.getAll(); }
    public void create(int idcliente, LocalDate dataEstimada)    { service.create(idcliente, dataEstimada); }
    public void updateEstado(int id, String estado)              { service.updateEstado(id, estado); }
    public void delete(int id)                                   { service.delete(id); }
}
