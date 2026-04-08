package com.gestorcerveja.model;

import java.time.LocalDate;

public class Lote {
    private int idlote;
    private int idpedido;
    private int idreceita;
    private double litros;
    private LocalDate dataProducao;
    private int idveiculo;
    private int idrequestProducao;

    public Lote(int idlote, int idpedido, int idreceita, double litros,
                LocalDate dataProducao, int idveiculo, int idrequestProducao) {
        this.idlote            = idlote;
        this.idpedido          = idpedido;
        this.idreceita         = idreceita;
        this.litros            = litros;
        this.dataProducao      = dataProducao;
        this.idveiculo         = idveiculo;
        this.idrequestProducao = idrequestProducao;
    }

    public int getId()                 { return idlote; }
    public int getIdpedido()           { return idpedido; }
    public int getIdreceita()          { return idreceita; }
    public double getLitros()          { return litros; }
    public LocalDate getDataProducao() { return dataProducao; }
    public int getIdveiculo()          { return idveiculo; }
    public int getIdrequestProducao()  { return idrequestProducao; }

    public void setLitros(double litros)        { this.litros = litros; }
    public void setDataProducao(LocalDate d)    { this.dataProducao = d; }
    public void setIdveiculo(int idveiculo)     { this.idveiculo = idveiculo; }

    @Override
    public String toString() {
        return "[" + idlote + "] Receita: " + idreceita + " | " + litros + "L | " + dataProducao;
    }
}