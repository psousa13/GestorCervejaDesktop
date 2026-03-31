package controller;

import model.Ingrediente;
import service.IngredienteService;

import java.sql.SQLException;
import java.util.List;

public class IngredienteController {
    private final IngredienteService service = new IngredienteService();

    public void listAll() {
        try {
            List<Ingrediente> list = service.getAll();
            if (list.isEmpty()) { System.out.println("No ingredientes found."); return; }
            list.forEach(System.out::println);
        } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void listLowStock() {
        try {
            List<Ingrediente> list = service.getLowStock();
            if (list.isEmpty()) { System.out.println("All stocks OK."); return; }
            System.out.println("LOW STOCK:");
            list.forEach(System.out::println);
        } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void create(String nome, String unidade, double stockAtual, double stockMinimo) {
        try {
            service.create(nome, unidade, stockAtual, stockMinimo);
            System.out.println("Ingrediente created.");
        } catch (IllegalArgumentException e) { System.out.println("Validation: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void updateStock(int id, double novoStock) {
        try {
            service.updateStock(id, novoStock);
            System.out.println("Stock updated.");
        } catch (IllegalArgumentException e) { System.out.println("Validation: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void delete(int id) {
        try {
            service.delete(id);
            System.out.println("Ingrediente " + id + " deleted.");
        } catch (IllegalArgumentException e) { System.out.println("Error: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }
}