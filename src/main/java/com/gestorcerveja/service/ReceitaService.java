package com.gestorcerveja.service;
import com.gestorcerveja.model.Receita;
import com.gestorcerveja.repository.ReceitaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ReceitaService {
    private final ReceitaRepository repo;
    public ReceitaService(ReceitaRepository repo) { this.repo = repo; }
    public List<Receita> getAll() { return repo.findAll(); }
    public Receita getById(int id) { return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Receita " + id + " não encontrada.")); }
    public double getActivePrice(int id) { return repo.findActivePrice(id).map(p -> p.getPrecoPorLitro()).orElse(0.0); }
    public void create(String nome, String descricao) { if(nome==null||nome.isBlank()) throw new IllegalArgumentException("Nome obrigatório."); repo.insert(nome,descricao); }
    public void update(int id, String nome, String descricao) { if(nome==null||nome.isBlank()) throw new IllegalArgumentException("Nome obrigatório."); getById(id); repo.update(id,nome,descricao); }
    public void setPrice(int id, double preco) { getById(id); repo.setPrice(id,preco); }
    public void delete(int id) { getById(id); repo.delete(id); }
}
