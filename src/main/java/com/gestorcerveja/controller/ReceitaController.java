package controller;

import model.Receita;
import service.ReceitaService;

import java.sql.SQLException;
import java.util.List;

public class ReceitaController {
    private final ReceitaService service = new ReceitaService();

    public void listAll() {
        try {
            List<Receita> list = service.getAll();
            if (list.isEmpty()) { System.out.println("No receitas found."); return; }
            list.forEach(System.out::println);
        } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void create(String nome, String descricao) {
        try {
            service.create(nome, descricao);
            System.out.println("Receita created.");
        } catch (IllegalArgumentException e) { System.out.println("Validation: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void update(int id, String nome, String descricao) {
        try {
            service.update(id, nome, descricao);
            System.out.println("Receita updated.");
        } catch (IllegalArgumentException e) { System.out.println("Error: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void setPrice(int idreceita, double preco) {
        try {
            service.setPrice(idreceita, preco);
            System.out.println("Price set: " + preco + "€/L");
        } catch (IllegalArgumentException e) { System.out.println("Validation: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void getActivePrice(int idreceita) {
        try {
            double price = service.getActivePrice(idreceita);
            System.out.println("Active price: " + price + "€/L");
        } catch (IllegalStateException e) { System.out.println("Error: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void delete(int id) {
        try {
            service.delete(id);
            System.out.println("Receita " + id + " deleted.");
        } catch (IllegalArgumentException e) { System.out.println("Error: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }
}