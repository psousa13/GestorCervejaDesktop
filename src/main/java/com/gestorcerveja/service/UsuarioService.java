package com.gestorcerveja.service;

import com.gestorcerveja.model.Usuario;
import com.gestorcerveja.repository.UsuarioRepository;

import java.sql.SQLException;
import java.util.List;

public class UsuarioService {
    private final UsuarioRepository repo = new UsuarioRepository();

    public List<Usuario> getAll() throws SQLException { return repo.findAll(); }

    public Usuario getById(int id) throws SQLException {
        Usuario u = repo.findById(id);
        if (u == null) throw new IllegalArgumentException("Utilizador " + id + " não encontrado.");
        return u;
    }

    public Usuario login(String nome, String senha) throws SQLException {
        Usuario u = repo.findByNome(nome);
        if (u == null || !u.getSenha().equals(senha))
            throw new IllegalArgumentException("Credenciais inválidas.");
        return u;
    }

    public void create(String nome, String senha, int idrole) throws SQLException {
        if (nome == null || nome.isBlank())  throw new IllegalArgumentException("Nome é obrigatório.");
        if (senha == null || senha.isBlank()) throw new IllegalArgumentException("Senha é obrigatória.");
        repo.insert(new Usuario(0, nome, senha, idrole));
    }

    /**
     * Atualiza o perfil do utilizador autenticado.
     * Se {@code novaSenha} estiver vazia, apenas o nome é alterado.
     */
    public void updateSelf(int id, String novoNome, String novaSenha) throws SQLException {
        if (novoNome == null || novoNome.isBlank())
            throw new IllegalArgumentException("O nome não pode estar vazio.");
        repo.update(id, novoNome, novaSenha);
    }

    public void delete(int id) throws SQLException {
        getById(id);
        repo.delete(id);
    }
}
