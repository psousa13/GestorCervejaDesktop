package service;

import model.Role;
import repository.RoleRepository;

import java.sql.SQLException;
import java.util.List;

public class RoleService {
    private final RoleRepository repo = new RoleRepository();

    public List<Role> getAll() throws SQLException {
        return repo.findAll();
    }

    public Role getById(int id) throws SQLException {
        Role r = repo.findById(id);
        if (r == null) throw new IllegalArgumentException("Role " + id + " not found.");
        return r;
    }

    public void create(String nome, String descricao) throws SQLException {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome is required.");
        repo.insert(new Role(0, nome, descricao));
    }

    public void delete(int id) throws SQLException {
        getById(id);
        repo.delete(id);
    }
}