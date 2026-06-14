package com.gestorcerveja.controller;
import com.gestorcerveja.model.Ingrediente; import com.gestorcerveja.service.IngredienteService;
import org.springframework.stereotype.Component; import java.util.List;
@Component public class IngredienteController {
    private final IngredienteService s; public IngredienteController(IngredienteService s){this.s=s;}
    public List<Ingrediente> listAll()      { return s.getAll(); }
    public List<Ingrediente> listLowStock() { return s.getLowStock(); }
    public void create(String nome, String unidade, double sa, double sm) { s.create(nome,unidade,sa,sm); }
    public void update(int id, String nome, String unidade, double sa, double sm) { s.update(id,nome,unidade,sa,sm); }
    public void updateStock(int id, double stock) { s.updateStock(id,stock); }
    public void delete(int id) { s.delete(id); }
}
