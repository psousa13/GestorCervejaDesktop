package com.gestorcerveja.controller;

import com.gestorcerveja.model.Pedido;
import com.gestorcerveja.model.PedidoItem;
import com.gestorcerveja.service.PedidoService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PedidoController {
    private final PedidoService service = new PedidoService();

    public List<Pedido> listAll() throws SQLException {
        return service.getAll();
    }

    public List<Pedido> listByCliente(int idcliente) throws SQLException {
        return service.getByCliente(idcliente);
    }

    public List<PedidoItem> listItems(int idpedido) throws SQLException {
        return service.getItems(idpedido);
    }

    /** Cria um pedido e devolve o id gerado. */
    public int create(int idcliente, LocalDate dataEstimada) throws SQLException {
        return service.create(idcliente, dataEstimada);
    }

    public void addItem(int idpedido, int idreceita,
                        double litros, int grades) throws SQLException {
        service.addItem(idpedido, idreceita, litros, grades);
    }

    public void updateEstado(int id, String estado) throws SQLException {
        service.updateEstado(id, estado);
    }

    public void delete(int id) throws SQLException {
        service.delete(id);
    }
}
