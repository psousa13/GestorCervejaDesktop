package com.gestorcerveja.service;
import com.gestorcerveja.model.*;
import com.gestorcerveja.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository repo;
    public ClienteService(ClienteRepository repo) { this.repo = repo; }

    public List<Cliente>     getAll()        { return repo.findAll(); }
    public Cliente           getById(int id) { return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente " + id + " não encontrado.")); }
    public ClienteParticular getParticular(int id) { return repo.findParticular(id).orElseThrow(() -> new IllegalArgumentException("ClienteParticular " + id + " não encontrado.")); }
    public ClienteRevendedor getRevendedor(int id) { return repo.findRevendedor(id).orElseThrow(() -> new IllegalArgumentException("ClienteRevendedor " + id + " não encontrado.")); }

    @Transactional
    public void createParticular(String email, String telefone, String nomeCompleto, String nif) {
        if (nomeCompleto == null || nomeCompleto.isBlank()) throw new IllegalArgumentException("Nome obrigatório.");
        ClienteParticular cp = new ClienteParticular(0, email, telefone, null, nomeCompleto, nif);
        int id = repo.insertBase(cp);
        repo.insertParticular(id, cp);
    }

    @Transactional
    public void createRevendedor(String email, String telefone, String nomeEmpresa, String vat, String contacto, String dep, String telEmp, String nota) {
        if (nomeEmpresa == null || nomeEmpresa.isBlank()) throw new IllegalArgumentException("Nome empresa obrigatório.");
        ClienteRevendedor cr = new ClienteRevendedor(0, email, telefone, null, nomeEmpresa, vat, contacto, dep, telEmp, nota);
        int id = repo.insertBase(cr);
        repo.insertRevendedor(id, cr);
    }

    public void updateParticular(int id, String email, String telefone, String nome, String nif) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome obrigatório.");
        getById(id);
        repo.updateParticular(id, email, telefone, nome, nif);
    }

    public void delete(int id) { getById(id); repo.delete(id); }

    /** Encontra ou cria um cliente Particular pelo email. */
    public int findOrCreateParticular(String email, String telefone, String nomeCompleto) {
        return repo.findOrCreateParticular(email, telefone, nomeCompleto);
    }

    /** Encontra ou cria um cliente Revendedor pelo email. */
    public int findOrCreateRevendedor(String email, String telefone, String nomeEmpresa, String vat) {
        return repo.findOrCreateRevendedor(email, telefone, nomeEmpresa, vat);
    }
}
