package com.gestorcerveja.service;

import com.gestorcerveja.model.Usuario;
import com.gestorcerveja.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository repo;
    public UsuarioService(UsuarioRepository repo) { this.repo = repo; }

    public List<Usuario> getAll() { return repo.findAll(); }

    public Usuario getById(int id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Utilizador " + id + " não encontrado."));
    }
    public Usuario login(String nome, String senha) {
        Usuario u = repo.findByNome(nome).orElseThrow(() -> new IllegalArgumentException("Credenciais inválidas."));
        if (!u.getSenha().equals(senha)) throw new IllegalArgumentException("Credenciais inválidas.");
        return u;
    }
    public void create(String nome, String senha, int idrole) {
        if (nome == null || nome.isBlank())  throw new IllegalArgumentException("Nome é obrigatório.");
        if (senha == null || senha.isBlank()) throw new IllegalArgumentException("Senha é obrigatória.");
        repo.insert(new Usuario(0, nome, senha, idrole));
    }
    public void updateSelf(int id, String novoNome, String novaSenha) {
        if (novoNome == null || novoNome.isBlank()) throw new IllegalArgumentException("O nome não pode estar vazio.");
        repo.update(id, novoNome, novaSenha);
    }
    public void delete(int id) { getById(id); repo.delete(id); }
}
