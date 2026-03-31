package controller;

import model.Cliente;
import model.ClienteParticular;
import model.ClienteRevendedor;
import service.ClienteService;

import java.sql.SQLException;
import java.util.List;

public class ClienteController {
    private final ClienteService service = new ClienteService();

    public void listAll() {
        try {
            List<Cliente> list = service.getAll();
            if (list.isEmpty()) { System.out.println("No clientes found."); return; }
            list.forEach(System.out::println);
        } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void getParticular(int id) {
        try {
            ClienteParticular cp = service.getParticular(id);
            System.out.println(cp);
        } catch (IllegalArgumentException e) { System.out.println("Error: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void getRevendedor(int id) {
        try {
            ClienteRevendedor cr = service.getRevendedor(id);
            System.out.println(cr);
        } catch (IllegalArgumentException e) { System.out.println("Error: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void createParticular(String email, String telefone, String nomeCompleto, String nif) {
        try {
            service.createParticular(email, telefone, nomeCompleto, nif);
            System.out.println("ClienteParticular created.");
        } catch (IllegalArgumentException e) { System.out.println("Validation: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void createRevendedor(String email, String telefone, String nomeEmpresa, String vatEmpresa,
                                 String contacto, String departamento, String telEmpresa, String nota) {
        try {
            service.createRevendedor(email, telefone, nomeEmpresa, vatEmpresa, contacto, departamento, telEmpresa, nota);
            System.out.println("ClienteRevendedor created.");
        } catch (IllegalArgumentException e) { System.out.println("Validation: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void delete(int id) {
        try {
            service.delete(id);
            System.out.println("Cliente " + id + " deleted.");
        } catch (IllegalArgumentException e) { System.out.println("Error: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }
}