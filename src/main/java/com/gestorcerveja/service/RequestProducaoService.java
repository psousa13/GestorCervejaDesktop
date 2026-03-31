package service;

import model.RequestProducao;
import repository.RequestProducaoRepository;

import java.sql.SQLException;
import java.util.List;

public class RequestProducaoService {
    private final RequestProducaoRepository repo = new RequestProducaoRepository();

    public List<RequestProducao> getAll() throws SQLException {
        return repo.findAll();
    }

    public RequestProducao getById(int id) throws SQLException {
        RequestProducao r = repo.findById(id);
        if (r == null) throw new IllegalArgumentException("RequestProducao " + id + " not found.");
        return r;
    }

    public int create(int idusuario) throws SQLException {
        return repo.insert(new RequestProducao(0, idusuario, "Pendente", null, null));
    }

    public void concluir(int id) throws SQLException {
        getById(id);
        repo.updateEstado(id, "Concluido");
    }
}