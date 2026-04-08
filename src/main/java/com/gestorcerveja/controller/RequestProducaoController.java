package com.gestorcerveja.controller;

import com.gestorcerveja.model.RequestProducao;
import com.gestorcerveja.service.RequestProducaoService;

import java.sql.SQLException;
import java.util.List;

public class RequestProducaoController {
    private final RequestProducaoService service = new RequestProducaoService();

    public void listAll() {
        try {
            List<RequestProducao> list = service.getAll();
            if (list.isEmpty()) { System.out.println("No requests found."); return; }
            list.forEach(System.out::println);
        } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void create(int idusuario) {
        try {
            int id = service.create(idusuario);
            System.out.println("RequestProducao created with id: " + id);
        } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void concluir(int id) {
        try {
            service.concluir(id);
            System.out.println("RequestProducao " + id + " concluido.");
        } catch (IllegalArgumentException e) { System.out.println("Error: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }
}