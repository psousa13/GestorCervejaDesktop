package com.gestorcerveja.controller;

import com.gestorcerveja.model.Usuario;
import com.gestorcerveja.service.UsuarioService;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class UsuarioController {
    private final UsuarioService service;
    public UsuarioController(UsuarioService service) { this.service = service; }

    public List<Usuario> listAll()                                  { return service.getAll(); }
    public void create(String nome, String senha, int idrole)       { service.create(nome, senha, idrole); }
    public void updateSelf(int id, String novoNome, String novaSenha){ service.updateSelf(id, novoNome, novaSenha); }
    public void delete(int id)                                      { service.delete(id); }
}
