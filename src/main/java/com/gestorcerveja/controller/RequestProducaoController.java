package com.gestorcerveja.controller;
import com.gestorcerveja.model.RequestProducao; import com.gestorcerveja.service.RequestProducaoService;
import org.springframework.stereotype.Component; import java.util.List;
@Component public class RequestProducaoController {
    private final RequestProducaoService s; public RequestProducaoController(RequestProducaoService s){this.s=s;}
    public List<RequestProducao> listAll()         { return s.getAll(); }
    public void create(int idusuario)               { s.create(idusuario); }
    public void concluir(int id)                    { s.concluir(id); }
}
