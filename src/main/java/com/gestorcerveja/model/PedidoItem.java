package com.gestorcerveja.model;

public class PedidoItem {
    private int iditem;
    private int idpedido;
    private int idreceita;
    private double quantidadeLitros;
    private int quantidadeGrades;

    public PedidoItem(int iditem, int idpedido, int idreceita, double quantidadeLitros, int quantidadeGrades) {
        this.iditem           = iditem;
        this.idpedido         = idpedido;
        this.idreceita        = idreceita;
        this.quantidadeLitros = quantidadeLitros;
        this.quantidadeGrades = quantidadeGrades;
    }

    public int getId()                  { return iditem; }
    public int getIdpedido()            { return idpedido; }
    public int getIdreceita()           { return idreceita; }
    public double getQuantidadeLitros() { return quantidadeLitros; }
    public int getQuantidadeGrades()    { return quantidadeGrades; }

    @Override
    public String toString() {
        return "[item " + iditem + "] Pedido: " + idpedido + " | Receita: " + idreceita
                + " | " + quantidadeLitros + "L | " + quantidadeGrades + " grades";
    }
}