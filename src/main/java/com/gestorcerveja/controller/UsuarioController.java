package com.gestorcerveja.controller;

import com.gestorcerveja.model.Usuario;
import com.gestorcerveja.service.UsuarioService;

import java.sql.SQLException;
import java.util.List;

public class UsuarioController {
    private final UsuarioService service = new UsuarioService();

    public List<Usuario> listAll() throws SQLException {
        return service.getAll();
    }

    public void create(String nome, String senha, int idrole) throws SQLException {
        service.create(nome, senha, idrole);
    }

    public void delete(int id) throws SQLException {
        service.delete(id);
    }
}
