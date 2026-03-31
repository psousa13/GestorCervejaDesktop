package service;

import model.Cliente;
import model.ClienteParticular;
import model.ClienteRevendedor;
import repository.ClienteRepository;

import java.sql.SQLException;
import java.util.List;

public class ClienteService {
    private final ClienteRepository repo = new ClienteRepository();

    public List<Cliente> getAll() throws SQLException {
        return repo.findAll();
    }

    public Cliente getById(int id) throws SQLException {
        Cliente c = repo.findById(id);
        if (c == null) throw new IllegalArgumentException("Cliente " + id + " not found.");
        return c;
    }

    public ClienteParticular getParticular(int id) throws SQLException {
        ClienteParticular cp = repo.findParticular(id);
        if (cp == null) throw new IllegalArgumentException("ClienteParticular " + id + " not found.");
        return cp;
    }

    public ClienteRevendedor getRevendedor(int id) throws SQLException {
        ClienteRevendedor cr = repo.findRevendedor(id);
        if (cr == null) throw new IllegalArgumentException("ClienteRevendedor " + id + " not found.");
        return cr;
    }

    public void createParticular(String email, String telefone, String nomeCompleto, String nif) throws SQLException {
        if (nomeCompleto == null || nomeCompleto.isBlank()) throw new IllegalArgumentException("Nome completo is required.");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email is required.");
        ClienteParticular cp = new ClienteParticular(0, email, telefone, null, nomeCompleto, nif);
        int newId = repo.insert(cp);
        repo.insertParticular(newId, cp);
    }

    public void createRevendedor(String email, String telefone, String nomeEmpresa, String vatEmpresa,
                                 String contacto, String departamento, String telEmpresa, String nota) throws SQLException {
        if (nomeEmpresa == null || nomeEmpresa.isBlank()) throw new IllegalArgumentException("Nome empresa is required.");
        ClienteRevendedor cr = new ClienteRevendedor(0, email, telefone, null, nomeEmpresa, vatEmpresa, contacto, departamento, telEmpresa, nota);
        int newId = repo.insert(cr);
        repo.insertRevendedor(newId, cr);
    }

    public void delete(int id) throws SQLException {
        getById(id);
        repo.delete(id);
    }
}