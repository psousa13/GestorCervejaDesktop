package com.gestorcerveja.service;
import com.gestorcerveja.model.Ingrediente;
import com.gestorcerveja.repository.IngredienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class IngredienteService {
    private final IngredienteRepository repo;
    public IngredienteService(IngredienteRepository repo) { this.repo = repo; }
    public List<Ingrediente> getAll()      { return repo.findAll(); }
    public List<Ingrediente> getLowStock() { return repo.findBelowMinStock(); }
    public Ingrediente getById(int id) { return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Ingrediente " + id + " não encontrado.")); }
    public void create(String nome, String unidade, double sa, double sm) { if (nome==null||nome.isBlank()) throw new IllegalArgumentException("Nome obrigatório."); repo.insert(new Ingrediente(0,nome,unidade,sa,sm)); }
    public void update(int id, String nome, String unidade, double sa, double sm) { if (nome==null||nome.isBlank()) throw new IllegalArgumentException("Nome obrigatório."); getById(id); repo.update(id,nome,unidade,sa,sm); }
    public void updateStock(int id, double s) { if(s<0) throw new IllegalArgumentException("Stock não pode ser negativo."); getById(id); repo.updateStock(id,s); }
    public void delete(int id) { getById(id); repo.delete(id); }
}
