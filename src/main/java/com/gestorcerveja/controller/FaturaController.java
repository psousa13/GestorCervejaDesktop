package controller;

import model.Fatura;
import model.FaturaItem;
import service.FaturaService;

import java.sql.SQLException;
import java.util.List;

public class FaturaController {
    private final FaturaService service = new FaturaService();

    public void getByPedido(int idpedido) {
        try {
            Fatura f = service.getByPedido(idpedido);
            System.out.println(f);
        } catch (IllegalArgumentException e) { System.out.println("Error: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void listItems(int idfatura) {
        try {
            List<FaturaItem> items = service.getItems(idfatura);
            if (items.isEmpty()) { System.out.println("No items in fatura " + idfatura); return; }
            items.forEach(System.out::println);
        } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void generate(int idpedido) {
        try {
            int id = service.generateFromPedido(idpedido);
            System.out.println("Fatura generated with id: " + id);
        } catch (IllegalArgumentException | IllegalStateException e) { System.out.println("Error: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void updateEstado(int id, String estado) {
        try {
            service.updateEstado(id, estado);
            System.out.println("Fatura " + id + " estado: " + estado);
        } catch (IllegalArgumentException e) { System.out.println("Validation: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }
}