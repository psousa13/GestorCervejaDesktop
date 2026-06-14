package com.gestorcerveja.controller;
import com.gestorcerveja.model.Usuario; import com.gestorcerveja.service.UsuarioService;
import org.springframework.stereotype.Component;
@Component public class LoginController {
    private final UsuarioService s; public LoginController(UsuarioService s){this.s=s;}
    public Usuario login(String nome, String senha) { return s.login(nome,senha); }
}
