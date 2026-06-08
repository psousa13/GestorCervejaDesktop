package com.gestorcerveja.controller;

import com.gestorcerveja.model.Usuario;
import com.gestorcerveja.service.UsuarioService;
import org.springframework.stereotype.Component;

@Component
public class LoginController {
    private final UsuarioService service;
    public LoginController(UsuarioService service) { this.service = service; }
    public Usuario login(String nome, String senha) { return service.login(nome, senha); }
}
