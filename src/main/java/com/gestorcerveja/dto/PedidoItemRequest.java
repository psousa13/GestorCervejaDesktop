package com.gestorcerveja.dto;

public class PedidoItemRequest {
    private int    idreceita;
    private double quantidadeLitros;
    private int    quantidadeGrades;
    private double precoUnitario;

    public int    getIdreceita()        { return idreceita; }
    public double getQuantidadeLitros() { return quantidadeLitros; }
    public int    getQuantidadeGrades() { return quantidadeGrades; }
    public double getPrecoUnitario()    { return precoUnitario; }
    public void   setIdreceita(int i)            { this.idreceita = i; }
    public void   setQuantidadeLitros(double q)  { this.quantidadeLitros = q; }
    public void   setQuantidadeGrades(int q)     { this.quantidadeGrades = q; }
    public void   setPrecoUnitario(double p)     { this.precoUnitario = p; }
}
