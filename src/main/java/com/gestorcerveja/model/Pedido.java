package com.gestorcerveja.model;

import java.time.LocalDate;

public class Pedido {
    private int idpedido;
    private int idcliente;
    private LocalDate dataPedido;
    private String estado;
    private LocalDate dataEstimadaConclusao;
    private double totalLitros;
    private int totalGrade;

    public Pedido(int idpedido, int idcliente, LocalDate dataPedido, String estado,
                  LocalDate dataEstimadaConclusao, double totalLitros, int totalGrade) {
        this.idpedido              = idpedido;
        this.idcliente             = idcliente;
        this.dataPedido            = dataPedido;
        this.estado                = estado;
        this.dataEstimadaConclusao = dataEstimadaConclusao;
        this.totalLitros           = totalLitros;
        this.totalGrade            = totalGrade;
    }

    public int getId()                          { return idpedido; }
    public int getIdcliente()                   { return idcliente; }
    public LocalDate getDataPedido()            { return dataPedido; }
    public String getEstado()                   { return estado; }
    public LocalDate getDataEstimadaConclusao() { return dataEstimadaConclusao; }
    public double getTotalLitros()              { return totalLitros; }
    public int getTotalGrade()                  { return totalGrade; }

    public void setEstado(String estado)             { this.estado = estado; }
    public void setDataEstimadaConclusao(LocalDate d){ this.dataEstimadaConclusao = d; }
    public void setTotalLitros(double v)             { this.totalLitros = v; }
    public void setTotalGrade(int v)                 { this.totalGrade = v; }

    @Override
    public String toString() {
        return "[" + idpedido + "] Cliente: " + idcliente + " | " + estado + " | " + dataPedido;
    }
}