package controller;

import model.Pedido;
import model.PedidoItem;
import service.PedidoService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PedidoController {
    private final PedidoService service = new PedidoService();

    public void listAll() {
        try {
            List<Pedido> list = service.getAll();
            if (list.isEmpty()) { System.out.println("No pedidos found."); return; }
            list.forEach(System.out::println);
        } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void listByCliente(int idcliente) {
        try {
            List<Pedido> list = service.getByCliente(idcliente);
            if (list.isEmpty()) { System.out.println("No pedidos for cliente " + idcliente); return; }
            list.forEach(System.out::println);
        } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void listItems(int idpedido) {
        try {
            List<PedidoItem> items = service.getItems(idpedido);
            if (items.isEmpty()) { System.out.println("No items in pedido " + idpedido); return; }
            items.forEach(System.out::println);
        } catch (IllegalArgumentException e) { System.out.println("Error: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void create(int idcliente, LocalDate dataEstimada) {
        try {
            int id = service.create(idcliente, dataEstimada);
            System.out.println("Pedido created with id: " + id);
        } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void addItem(int idpedido, int idreceita, double litros, int grades) {
        try {
            service.addItem(idpedido, idreceita, litros, grades);
            System.out.println("Item added to pedido " + idpedido);
        } catch (IllegalArgumentException e) { System.out.println("Validation: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void updateEstado(int id, String estado) {
        try {
            service.updateEstado(id, estado);
            System.out.println("Pedido " + id + " estado: " + estado);
        } catch (IllegalArgumentException e) { System.out.println("Validation: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void delete(int id) {
        try {
            service.delete(id);
            System.out.println("Pedido " + id + " deleted.");
        } catch (IllegalArgumentException e) { System.out.println("Error: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }
}