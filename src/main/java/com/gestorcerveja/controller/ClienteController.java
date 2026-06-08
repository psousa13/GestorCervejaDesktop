package com.gestorcerveja.controller;

import com.gestorcerveja.model.*;
import com.gestorcerveja.service.ClienteService;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ClienteController {
    private final ClienteService service;
    public ClienteController(ClienteService service) { this.service = service; }

    public List<Cliente>     listAll()                { return service.getAll(); }
    public ClienteParticular getParticular(int id)    { return service.getParticular(id); }
    public ClienteRevendedor getRevendedor(int id)    { return service.getRevendedor(id); }
    public void createParticular(String email, String telefone, String nomeCompleto, String nif) { service.createParticular(email, telefone, nomeCompleto, nif); }
    public void updateParticular(int id, String email, String telefone, String nomeCompleto, String nif) { service.updateParticular(id, email, telefone, nomeCompleto, nif); }
    public void delete(int id)                        { service.delete(id); }
}
