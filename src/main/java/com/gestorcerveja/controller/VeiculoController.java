package com.gestorcerveja.controller;

import com.gestorcerveja.model.Veiculo;
import com.gestorcerveja.service.VeiculoService;

import java.sql.SQLException;
import java.util.List;

public class VeiculoController {
    private final VeiculoService service = new VeiculoService();

    public void listAll() {
        try {
            List<Veiculo> list = service.getAll();
            if (list.isEmpty()) { System.out.println("No veiculos found."); return; }
            list.forEach(System.out::println);
        } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void create(String matricula, String marca, String cor, String nome, double capacidade, String tipo) {
        try {
            service.create(matricula, marca, cor, nome, capacidade, tipo);
            System.out.println("Veiculo created.");
        } catch (IllegalArgumentException e) { System.out.println("Validation: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void addCarga(int id, double litros) {
        try {
            service.addCarga(id, litros);
            System.out.println("Carga added.");
        } catch (IllegalStateException | IllegalArgumentException e) { System.out.println("Error: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void clearCarga(int id) {
        try {
            service.clearCarga(id);
            System.out.println("Veiculo " + id + " cleared.");
        } catch (IllegalArgumentException e) { System.out.println("Error: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void delete(int id) {
        try {
            service.delete(id);
            System.out.println("Veiculo " + id + " deleted.");
        } catch (IllegalArgumentException e) { System.out.println("Error: " + e.getMessage()); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }
}