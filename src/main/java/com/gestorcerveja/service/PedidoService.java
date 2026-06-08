package com.gestorcerveja.service;

import com.gestorcerveja.model.Pedido;
import com.gestorcerveja.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository repo;
    public PedidoService(PedidoRepository repo) { this.repo = repo; }

    public List<Pedido> getAll()       { return repo.findAll(); }
    public Pedido getById(int id) { return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Pedido " + id + " não encontrado.")); }

    public void create(int idcliente, LocalDate dataEstimada) {
        if (idcliente <= 0) throw new IllegalArgumentException("ID cliente inválido.");
        repo.insert(idcliente, dataEstimada);
    }
    public void updateEstado(int id, String estado) { getById(id); repo.updateEstado(id, estado); }
    public void delete(int id) { getById(id); repo.delete(id); }
}
