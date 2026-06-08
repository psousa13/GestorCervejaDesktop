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

    public List<Cliente>     getAll()       { return repo.findAll(); }
    public Cliente           getById(int id){ return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente " + id + " não encontrado.")); }
    public ClienteParticular getParticular(int id) { return repo.findParticular(id).orElseThrow(() -> new IllegalArgumentException("ClienteParticular " + id + " não encontrado.")); }
    public ClienteRevendedor getRevendedor(int id) { return repo.findRevendedor(id).orElseThrow(() -> new IllegalArgumentException("ClienteRevendedor " + id + " não encontrado.")); }

    @Transactional
    public void createParticular(String email, String telefone, String nomeCompleto, String nif) {
        if (nomeCompleto == null || nomeCompleto.isBlank()) throw new IllegalArgumentException("Nome completo é obrigatório.");
        ClienteParticular cp = new ClienteParticular(0, email, telefone, null, nomeCompleto, nif);
        int newId = repo.insertBase(cp);
        repo.insertParticular(newId, cp);
    }

    @Transactional
    public void createRevendedor(String email, String telefone, String nomeEmpresa, String vatEmpresa,
                                 String contacto, String departamento, String telEmpresa, String nota) {
        if (nomeEmpresa == null || nomeEmpresa.isBlank()) throw new IllegalArgumentException("Nome empresa é obrigatório.");
        ClienteRevendedor cr = new ClienteRevendedor(0, email, telefone, null, nomeEmpresa, vatEmpresa, contacto, departamento, telEmpresa, nota);
        int newId = repo.insertBase(cr);
        repo.insertRevendedor(newId, cr);
    }

    public void updateParticular(int id, String email, String telefone, String nomeCompleto, String nif) {
        if (nomeCompleto == null || nomeCompleto.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
        getById(id);
        repo.updateParticular(id, email, telefone, nomeCompleto, nif);
    }
    public void delete(int id) { getById(id); repo.delete(id); }
}
