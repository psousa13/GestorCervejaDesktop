package com.gestorcerveja.model;

import java.time.LocalDate;

public class Fatura {
    private int idfatura;
    private int idpedido;
    private LocalDate dataEmissao;
    private double valorTotal;
    private String estado;

    public Fatura(int idfatura, int idpedido, LocalDate dataEmissao, double valorTotal, String estado) {
        this.idfatura    = idfatura;
        this.idpedido    = idpedido;
        this.dataEmissao = dataEmissao;
        this.valorTotal  = valorTotal;
        this.estado      = estado;
    }

    public int getId()                { return idfatura; }
    public int getIdpedido()          { return idpedido; }
    public LocalDate getDataEmissao() { return dataEmissao; }
    public double getValorTotal()     { return valorTotal; }
    public String getEstado()         { return estado; }

    public void setValorTotal(double v) { this.valorTotal = v; }
    public void setEstado(String estado){ this.estado = estado; }

    @Override
    public String toString() {
        return "[" + idfatura + "] Pedido: " + idpedido + " | " + valorTotal + "€ | " + estado;
    }
}