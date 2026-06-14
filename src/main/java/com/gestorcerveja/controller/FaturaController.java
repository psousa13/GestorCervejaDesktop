package com.gestorcerveja.controller;
import com.gestorcerveja.model.Fatura; import com.gestorcerveja.service.FaturaService;
import org.springframework.stereotype.Component; import java.util.List;
@Component public class FaturaController {
    private final FaturaService s; public FaturaController(FaturaService s){this.s=s;}
    public List<Fatura> listAll()      { return s.getAll(); }
    public Fatura getByPedido(int idp) { return s.getByPedido(idp); }
}
