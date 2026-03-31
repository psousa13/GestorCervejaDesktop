package service;

import model.Lote;
import repository.LoteRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class LoteService {
    private final LoteRepository repo           = new LoteRepository();
    private final VeiculoService veiculoService = new VeiculoService();

    public List<Lote> getAll() throws SQLException {
        return repo.findAll();
    }

    public Lote getById(int id) throws SQLException {
        Lote l = repo.findById(id);
        if (l == null) throw new IllegalArgumentException("Lote " + id + " not found.");
        return l;
    }

    public List<Lote> getByPedido(int idpedido) throws SQLException {
        return repo.findByPedido(idpedido);
    }

    public int create(int idpedido, int idreceita, double litros,
                      LocalDate dataProducao, int idveiculo, int idrequestProducao) throws SQLException {
        if (litros <= 0) throw new IllegalArgumentException("Litros must be positive.");
        veiculoService.addCarga(idveiculo, litros);
        return repo.insert(new Lote(0, idpedido, idreceita, litros, dataProducao, idveiculo, idrequestProducao));
    }

    public void delete(int id) throws SQLException {
        getById(id);
        repo.delete(id);
    }
}