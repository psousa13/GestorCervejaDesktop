package service;

import model.Pedido;
import model.PedidoItem;
import repository.PedidoRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PedidoService {
    private final PedidoRepository repo = new PedidoRepository();

    public List<Pedido> getAll() throws SQLException {
        return repo.findAll();
    }

    public Pedido getById(int id) throws SQLException {
        Pedido p = repo.findById(id);
        if (p == null) throw new IllegalArgumentException("Pedido " + id + " not found.");
        return p;
    }

    public List<Pedido> getByCliente(int idcliente) throws SQLException {
        return repo.findByCliente(idcliente);
    }

    public List<PedidoItem> getItems(int idpedido) throws SQLException {
        getById(idpedido);
        return repo.findItemsByPedido(idpedido);
    }

    public int create(int idcliente, LocalDate dataEstimada) throws SQLException {
        return repo.insert(new Pedido(0, idcliente, LocalDate.now(), "Pendente", dataEstimada, 0, 0));
    }

    public void addItem(int idpedido, int idreceita, double litros, int grades) throws SQLException {
        if (litros <= 0 && grades <= 0) throw new IllegalArgumentException("Must specify litros or grades.");
        getById(idpedido);
        repo.insertItem(new PedidoItem(0, idpedido, idreceita, litros, grades));
    }

    public void updateEstado(int id, String estado) throws SQLException {
        List<String> valid = List.of("Pendente", "Em Producao", "Concluido", "Cancelado");
        if (!valid.contains(estado)) throw new IllegalArgumentException("Invalid estado: " + estado);
        getById(id);
        repo.updateEstado(id, estado);
    }

    public void delete(int id) throws SQLException {
        getById(id);
        repo.delete(id);
    }
}