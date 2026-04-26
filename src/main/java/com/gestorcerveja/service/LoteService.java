package com.gestorcerveja.service;

import com.gestorcerveja.model.Lote;
import com.gestorcerveja.repository.LoteRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class LoteService {
    private final LoteRepository repo           = new LoteRepository();
    private final VeiculoService veiculoService = new VeiculoService();

    public List<Lote> getAll()      throws SQLException { return repo.findAll(); }
    public List<Lote> getByPedido(int idpedido) throws SQLException { return repo.findByPedido(idpedido); }

    public Lote getById(int id) throws SQLException {
        Lote l = repo.findById(id);
        if (l == null) throw new IllegalArgumentException("Lote " + id + " não encontrado.");
        return l;
    }

    public int create(int idpedido, int idreceita, double litros,
                      LocalDate dataProducao, int idveiculo, int idrequestProducao) throws SQLException {
        if (litros <= 0) throw new IllegalArgumentException("Litros deve ser positivo.");
        veiculoService.updateOcupacao(idveiculo, litros);
        return repo.insert(new Lote(0, idpedido, idreceita, litros, dataProducao, idveiculo, idrequestProducao));
    }

    public void updateVeiculo(int idlote, int idveiculo) throws SQLException {
        getById(idlote);
        repo.updateVeiculo(idlote, idveiculo);
    }

    public void delete(int id) throws SQLException { getById(id); repo.delete(id); }
}
