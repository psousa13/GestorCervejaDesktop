package com.gestorcerveja.controller;
import com.gestorcerveja.model.Receita; import com.gestorcerveja.service.ReceitaService;
import org.springframework.stereotype.Component; import java.util.List;
@Component public class ReceitaController {
    private final ReceitaService s; public ReceitaController(ReceitaService s){this.s=s;}
    public List<Receita> listAll() { return s.getAll(); }
    public double getActivePrice(int id) { return s.getActivePrice(id); }
    public void create(String nome, String descricao) { s.create(nome,descricao); }
    public void update(int id, String nome, String descricao) { s.update(id,nome,descricao); }
    public void setPrice(int id, double preco) { s.setPrice(id,preco); }
    public void delete(int id) { s.delete(id); }
}
