package service;

import model.Usuario;
import repository.UsuarioRepository;

import java.sql.SQLException;
import java.util.List;

public class UsuarioService {
    private final UsuarioRepository repo = new UsuarioRepository();

    public List<Usuario> getAll() throws SQLException {
        return repo.findAll();
    }

    public Usuario getById(int id) throws SQLException {
        Usuario u = repo.findById(id);
        if (u == null) throw new IllegalArgumentException("Usuario " + id + " not found.");
        return u;
    }

    public Usuario login(String nome, String senha) throws SQLException {
        Usuario u = repo.findByNome(nome);
        if (u == null || !u.getSenha().equals(senha))
            throw new IllegalArgumentException("Invalid credentials.");
        return u;
    }

    public void create(String nome, String senha, int idrole) throws SQLException {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome is required.");
        if (senha == null || senha.isBlank()) throw new IllegalArgumentException("Senha is required.");
        repo.insert(new Usuario(0, nome, senha, idrole));
    }

    public void delete(int id) throws SQLException {
        getById(id);
        repo.delete(id);
    }
}