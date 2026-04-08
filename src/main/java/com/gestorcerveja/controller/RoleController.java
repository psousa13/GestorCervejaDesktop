package com.gestorcerveja.controller;

import com.gestorcerveja.model.Role;
import com.gestorcerveja.service.RoleService;

import java.sql.SQLException;
import java.util.List;

public class RoleController {
    private final RoleService service = new RoleService();

    public void listAll() {
        try {
            List<Role> list = service.getAll();
            if (list.isEmpty()) { System.out.println("No roles found."); return; }
            list.forEach(System.out::println);
        } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void create(String nome, String descricao) {
        try {
            service.create(nome, descricao);
            System.out.println("Role created.");
        } catch (IllegalArgumentException e) { System.out.println("Validation: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void delete(int id) {
        try {
            service.delete(id);
            System.out.println("Role " + id + " deleted.");
        } catch (IllegalArgumentException e) { System.out.println("Error: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }
}