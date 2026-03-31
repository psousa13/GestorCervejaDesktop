package service;

import model.Fatura;
import model.FaturaItem;
import model.PedidoItem;
import repository.FaturaRepository;
import repository.PedidoRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class FaturaService {
    private final FaturaRepository faturaRepo   = new FaturaRepository();
    private final PedidoRepository pedidoRepo   = new PedidoRepository();
    private final ReceitaService receitaService = new ReceitaService();

    public Fatura getByPedido(int idpedido) throws SQLException {
        Fatura f = faturaRepo.findByPedido(idpedido);
        if (f == null) throw new IllegalArgumentException("No fatura for pedido " + idpedido);
        return f;
    }

    public List<FaturaItem> getItems(int idfatura) throws SQLException {
        return faturaRepo.findItemsByFatura(idfatura);
    }

    public int generateFromPedido(int idpedido) throws SQLException {
        if (faturaRepo.findByPedido(idpedido) != null)
            throw new IllegalStateException("Fatura already exists for pedido " + idpedido);
        List<PedidoItem> items = pedidoRepo.findItemsByPedido(idpedido);
        if (items.isEmpty()) throw new IllegalStateException("Pedido has no items.");

        double total = 0;
        for (PedidoItem item : items)
            total += item.getQuantidadeLitros() * receitaService.getActivePrice(item.getIdreceita());

        int newId = faturaRepo.insert(new Fatura(0, idpedido, LocalDate.now(), total, "Pendente"));

        for (PedidoItem item : items) {
            double price   = receitaService.getActivePrice(item.getIdreceita());
            double subtotal = item.getQuantidadeLitros() * price;
            faturaRepo.insertItem(new FaturaItem(0, newId, item.getIdreceita(), item.getQuantidadeLitros(), price, subtotal));
        }
        return newId;
    }

    public void updateEstado(int id, String estado) throws SQLException {
        List<String> valid = List.of("Pendente", "Paga", "Cancelada");
        if (!valid.contains(estado)) throw new IllegalArgumentException("Invalid estado: " + estado);
        faturaRepo.updateEstado(id, estado);
    }
}