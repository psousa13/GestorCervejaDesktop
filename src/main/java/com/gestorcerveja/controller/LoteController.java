package com.gestorcerveja.controller;

import com.gestorcerveja.model.Lote;
import com.gestorcerveja.service.LoteService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class LoteController {
    private final LoteService service = new LoteService();

    public void listAll() {
        try {
            List<Lote> list = service.getAll();
            if (list.isEmpty()) { System.out.println("No lotes found."); return; }
            list.forEach(System.out::println);
        } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void listByPedido(int idpedido) {
        try {
            List<Lote> list = service.getByPedido(idpedido);
            if (list.isEmpty()) { System.out.println("No lotes for pedido " + idpedido); return; }
            list.forEach(System.out::println);
        } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void create(int idpedido, int idreceita, double litros,
                       LocalDate dataProducao, int idveiculo, int idrequestProducao) {
        try {
            int id = service.create(idpedido, idreceita, litros, dataProducao, idveiculo, idrequestProducao);
            System.out.println("Lote created with id: " + id);
        } catch (IllegalArgumentException | IllegalStateException e) { System.out.println("Error: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void delete(int id) {
        try {
            service.delete(id);
            System.out.println("Lote " + id + " deleted.");
        } catch (IllegalArgumentException e) { System.out.println("Error: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }
}