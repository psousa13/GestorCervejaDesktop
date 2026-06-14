package com.gestorcerveja.controller;
import com.gestorcerveja.model.Lote; import com.gestorcerveja.service.LoteService;
import org.springframework.stereotype.Component; import java.time.LocalDate; import java.util.List;
@Component public class LoteController {
    private final LoteService s; public LoteController(LoteService s){this.s=s;}
    public List<Lote> listAll()             { return s.getAll(); }
    public List<Lote> listByPedido(int idp) { return s.getByPedido(idp); }
    public int  create(int idpedido, int idreceita, double litros, LocalDate data, int idveiculo, int idrequest) { return s.create(idpedido,idreceita,litros,data,idveiculo,idrequest); }
    public void updateVeiculo(int idlote, int idveiculo) { s.updateVeiculo(idlote,idveiculo); }
    public void delete(int id) { s.delete(id); }
}
