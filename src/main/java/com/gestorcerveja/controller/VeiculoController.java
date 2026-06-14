package com.gestorcerveja.controller;
import com.gestorcerveja.model.Veiculo; import com.gestorcerveja.service.VeiculoService;
import org.springframework.stereotype.Component; import java.util.List;
@Component public class VeiculoController {
    private final VeiculoService s; public VeiculoController(VeiculoService s){this.s=s;}
    public List<Veiculo> listAll() { return s.getAll(); }
    public void create(String matricula, String marca, String cor, String nome, double cap, String tipo) { s.create(matricula,marca,cor,nome,cap,tipo); }
    public void update(int id, String nome, String tipo, String marca, String cor, double cap) { s.update(id,nome,tipo,marca,cor,cap); }
    public void delete(int id) { s.delete(id); }
}
