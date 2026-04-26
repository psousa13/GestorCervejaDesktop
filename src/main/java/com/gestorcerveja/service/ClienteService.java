package com.gestorcerveja.service;

import com.gestorcerveja.model.Cliente;
import com.gestorcerveja.model.ClienteParticular;
import com.gestorcerveja.model.ClienteRevendedor;
import com.gestorcerveja.repository.ClienteRepository;

import java.sql.SQLException;
import java.util.List;

public class ClienteService {
    private final ClienteRepository repo = new ClienteRepository();

    public List<Cliente>     getAll()           throws SQLException { return repo.findAll(); }
    public ClienteParticular getParticular(int id) throws SQLException {
        ClienteParticular cp = repo.findParticular(id);
        if (cp == null) throw new IllegalArgumentException("ClienteParticular " + id + " não encontrado.");
        return cp;
    }
    public ClienteRevendedor getRevendedor(int id) throws SQLException {
        ClienteRevendedor cr = repo.findRevendedor(id);
        if (cr == null) throw new IllegalArgumentException("ClienteRevendedor " + id + " não encontrado.");
        return cr;
    }
    public Cliente getById(int id) throws SQLException {
        Cliente c = repo.findById(id);
        if (c == null) throw new IllegalArgumentException("Cliente " + id + " não encontrado.");
        return c;
    }

    public void createParticular(String email, String telefone, String nomeCompleto, String nif) throws SQLException {
        if (nomeCompleto == null || nomeCompleto.isBlank()) throw new IllegalArgumentException("Nome completo é obrigatório.");
        ClienteParticular cp = new ClienteParticular(0, email, telefone, null, nomeCompleto, nif);
        int newId = repo.insert(cp);
        repo.insertParticular(newId, cp);
    }

    public void createRevendedor(String email, String telefone, String nomeEmpresa, String vatEmpresa,
                                 String contacto, String departamento, String telEmpresa, String nota) throws SQLException {
        if (nomeEmpresa == null || nomeEmpresa.isBlank()) throw new IllegalArgumentException("Nome empresa é obrigatório.");
        ClienteRevendedor cr = new ClienteRevendedor(0, email, telefone, null, nomeEmpresa, vatEmpresa, contacto, departamento, telEmpresa, nota);
        int newId = repo.insert(cr);
        repo.insertRevendedor(newId, cr);
    }

    public void updateParticular(int id, String email, String telefone,
                                 String nomeCompleto, String nif) throws SQLException {
        if (nomeCompleto == null || nomeCompleto.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
        getById(id);
        repo.updateParticular(id, email, telefone, nomeCompleto, nif);
    }

    public void delete(int id) throws SQLException { getById(id); repo.delete(id); }
}
