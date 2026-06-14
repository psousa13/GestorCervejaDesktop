package com.gestorcerveja.service;
import com.gestorcerveja.model.Fatura;
import com.gestorcerveja.repository.FaturaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class FaturaService {
    private final FaturaRepository repo;
    public FaturaService(FaturaRepository repo) { this.repo = repo; }
    public List<Fatura> getAll() { return repo.findAll(); }
    public Fatura getByPedido(int idp) { return repo.findByPedido(idp).orElseThrow(() -> new IllegalArgumentException("Sem fatura para pedido " + idp)); }
}
