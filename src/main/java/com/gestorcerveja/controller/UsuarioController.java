package com.gestorcerveja.controller;
import com.gestorcerveja.model.Usuario; import com.gestorcerveja.service.UsuarioService;
import org.springframework.stereotype.Component; import java.util.List;
@Component public class UsuarioController {
    private final UsuarioService s; public UsuarioController(UsuarioService s){this.s=s;}
    public List<Usuario> listAll() { return s.getAll(); }
    public void create(String nome, String senha, int idrole) { s.create(nome,senha,idrole); }
    public void updateSelf(int id, String nome, String senha) { s.updateSelf(id,nome,senha); }
    public void delete(int id) { s.delete(id); }
}
