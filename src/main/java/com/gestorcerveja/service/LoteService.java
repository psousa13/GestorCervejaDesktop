package com.gestorcerveja.service;

import com.gestorcerveja.model.Lote;
import com.gestorcerveja.repository.LoteRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class LoteService {
    private final LoteRepository repo;
    private final VeiculoService veiculoService;
    public LoteService(LoteRepository repo, VeiculoService veiculoService) {
        this.repo = repo; this.veiculoService = veiculoService;
    }

    public List<Lote> getAll()              { return repo.findAll(); }
    public List<Lote> getByPedido(int idp)  { return repo.findByPedido(idp); }
    public Lote getById(int id) { return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Lote " + id + " não encontrado.")); }

    public int create(int idpedido, int idreceita, double litros, LocalDate dataProducao, int idveiculo, int idrequestProducao) {
        if (litros <= 0) throw new IllegalArgumentException("Litros deve ser positivo.");
        veiculoService.addCarga(idveiculo, litros);
        return repo.insert(new Lote(0, idpedido, idreceita, litros, dataProducao, idveiculo, idrequestProducao));
    }
    public void updateVeiculo(int idlote, int idveiculo) { getById(idlote); repo.updateVeiculo(idlote, idveiculo); }
    public void delete(int id) { getById(id); repo.delete(id); }
}
