package com.gestorcerveja.controller;

import com.gestorcerveja.model.Cliente;
import com.gestorcerveja.model.ClienteParticular;
import com.gestorcerveja.model.ClienteRevendedor;
import com.gestorcerveja.service.ClienteService;

import java.sql.SQLException;
import java.util.List;

public class ClienteController {
    private final ClienteService service = new ClienteService();

    public List<Cliente>    listAll()                throws SQLException { return service.getAll(); }
    public ClienteParticular getParticular(int id)   throws SQLException { return service.getParticular(id); }
    public ClienteRevendedor getRevendedor(int id)   throws SQLException { return service.getRevendedor(id); }

    public void createParticular(String email, String telefone, String nomeCompleto, String nif) throws SQLException {
        service.createParticular(email, telefone, nomeCompleto, nif);
    }
    public void createRevendedor(String email, String telefone, String nomeEmpresa, String vatEmpresa,
                                 String contacto, String departamento, String telEmpresa, String nota) throws SQLException {
        service.createRevendedor(email, telefone, nomeEmpresa, vatEmpresa, contacto, departamento, telEmpresa, nota);
    }

    public void updateParticular(int id, String email, String telefone,
                                 String nomeCompleto, String nif) throws SQLException {
        service.updateParticular(id, email, telefone, nomeCompleto, nif);
    }

    public void delete(int id) throws SQLException { service.delete(id); }
}
