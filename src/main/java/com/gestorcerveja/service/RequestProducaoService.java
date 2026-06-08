package com.gestorcerveja.service;

import com.gestorcerveja.model.RequestProducao;
import com.gestorcerveja.repository.RequestProducaoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RequestProducaoService {
    private final RequestProducaoRepository repo;
    public RequestProducaoService(RequestProducaoRepository repo) { this.repo = repo; }

    public List<RequestProducao> getAll()        { return repo.findAll(); }
    public RequestProducao getById(int id) { return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Request " + id + " não encontrado.")); }
    public void create(int idusuario)            { repo.insert(idusuario); }
    public void concluir(int id)                 { getById(id); repo.concluir(id); }
    public void updateEstado(int id, String e)   { getById(id); repo.updateEstado(id, e); }
}
