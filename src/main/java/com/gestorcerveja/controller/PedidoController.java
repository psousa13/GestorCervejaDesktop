package com.gestorcerveja.controller;
import com.gestorcerveja.model.Pedido; import com.gestorcerveja.service.PedidoService;
import org.springframework.stereotype.Component; import java.time.LocalDate; import java.util.List;
@Component public class PedidoController {
    private final PedidoService s; public PedidoController(PedidoService s){this.s=s;}
    public List<Pedido> listAll() { return s.getAll(); }
    public int  create(int idcliente, LocalDate dataEstimada) { return s.create(idcliente,dataEstimada); }
    public void updateEstado(int id, String estado) { s.updateEstado(id,estado); }
    public void delete(int id) { s.delete(id); }
}
