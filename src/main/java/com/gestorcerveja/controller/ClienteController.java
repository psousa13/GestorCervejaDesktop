package com.gestorcerveja.controller;
import com.gestorcerveja.model.*; import com.gestorcerveja.service.ClienteService;
import org.springframework.stereotype.Component; import java.util.List;
@Component public class ClienteController {
    private final ClienteService s; public ClienteController(ClienteService s){this.s=s;}
    public List<Cliente>     listAll()                { return s.getAll(); }
    public ClienteParticular getParticular(int id)    { return s.getParticular(id); }
    public ClienteRevendedor getRevendedor(int id)    { return s.getRevendedor(id); }
    public void createParticular(String email, String telefone, String nomeCompleto, String nif) { s.createParticular(email,telefone,nomeCompleto,nif); }
    public void updateParticular(int id, String email, String telefone, String nome, String nif) { s.updateParticular(id,email,telefone,nome,nif); }
    public void delete(int id) { s.delete(id); }
}
