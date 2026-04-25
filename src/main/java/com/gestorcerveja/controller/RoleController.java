package com.gestorcerveja.controller;

import com.gestorcerveja.model.Role;
import com.gestorcerveja.service.RoleService;

import java.sql.SQLException;
import java.util.List;

public class RoleController {
    private final RoleService service = new RoleService();

    public List<Role> listAll() throws SQLException {
        return service.getAll();
    }

    public void create(String nome, String descricao) throws SQLException {
        service.create(nome, descricao);
    }

    public void delete(int id) throws SQLException {
        service.delete(id);
    }
}
