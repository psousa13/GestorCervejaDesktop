package com.gestorcerveja.controller;

import com.gestorcerveja.model.Role;
import com.gestorcerveja.model.Usuario;
import com.gestorcerveja.service.RoleService;
import com.gestorcerveja.service.UsuarioService;
import com.gestorcerveja.ui.SessionManager;

import java.sql.SQLException;

public class LoginController {

    private final UsuarioService usuarioService = new UsuarioService();
    private final RoleService roleService  = new RoleService();

    /**
     * Autentica o utilizador, guarda a sessão e devolve o {@link Usuario}.
     *
     * @throws IllegalArgumentException se as credenciais forem inválidas
     * @throws SQLException             em caso de erro de base de dados
     */
    public Usuario login(String nome, String senha) throws SQLException {
        // Valida credenciais
        Usuario user = usuarioService.login(nome, senha);


        // Resolve a chave de role (ex: "admin", "producao", ...)
        Role role    = roleService.getById(user.getIdrole());
        String roleKey = (role != null) ? role.getNome() : "operador";

        // Persiste na sessão
        SessionManager.setUser(user);
        SessionManager.setRole(roleKey);

        return user;
    }

    /** Termina a sessão atual. */
    public void logout() {
        SessionManager.clear();
    }
}
