package com.gestorcerveja.model;

public class FaturaItem {
    private int iditem;
    private int idfatura;
    private int idreceita;
    private double quantidadeLitros;
    private double precoUnitario;
    private double subtotal;

    public FaturaItem(int iditem, int idfatura, int idreceita,
                      double quantidadeLitros, double precoUnitario, double subtotal) {
        this.iditem           = iditem;
        this.idfatura         = idfatura;
        this.idreceita        = idreceita;
        this.quantidadeLitros = quantidadeLitros;
        this.precoUnitario    = precoUnitario;
        this.subtotal         = subtotal;
    }

    public int getId()                  { return iditem; }
    public int getIdfatura()            { return idfatura; }
    public int getIdreceita()           { return idreceita; }
    public double getQuantidadeLitros() { return quantidadeLitros; }
    public double getPrecoUnitario()    { return precoUnitario; }
    public double getSubtotal()         { return subtotal; }

    @Override
    public String toString() {
        return "[item " + iditem + "] Receita: " + idreceita + " | "
                + quantidadeLitros + "L @ " + precoUnitario + "€ = " + subtotal + "€";
    }
}