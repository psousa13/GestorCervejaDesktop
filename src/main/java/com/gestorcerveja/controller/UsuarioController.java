package controller;

import model.Usuario;
import service.UsuarioService;

import java.sql.SQLException;
import java.util.List;

public class UsuarioController {
    private final UsuarioService service = new UsuarioService();

    public void listAll() {
        try {
            List<Usuario> list = service.getAll();
            if (list.isEmpty()) { System.out.println("No usuarios found."); return; }
            list.forEach(System.out::println);
        } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void login(String nome, String senha) {
        try {
            Usuario u = service.login(nome, senha);
            System.out.println("Logged in: " + u);
        } catch (IllegalArgumentException e) { System.out.println("Error: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void create(String nome, String senha, int idrole) {
        try {
            service.create(nome, senha, idrole);
            System.out.println("Usuario created.");
        } catch (IllegalArgumentException e) { System.out.println("Validation: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void delete(int id) {
        try {
            service.delete(id);
            System.out.println("Usuario " + id + " deleted.");
        } catch (IllegalArgumentException e) { System.out.println("Error: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }
}