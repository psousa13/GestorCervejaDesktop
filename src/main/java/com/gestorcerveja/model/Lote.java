package com.gestorcerveja.model;
import java.time.LocalDate;
public class Lote {
    private int id; private int idPedido; private int idReceita;
    private double litros; private LocalDate dataProducao; private int idVeiculo; private int idRequestProducao;
    public Lote() {}
    public Lote(int id, int idPedido, int idReceita, double litros, LocalDate dataProducao, int idVeiculo, int idRequestProducao) {
        this.id=id; this.idPedido=idPedido; this.idReceita=idReceita; this.litros=litros;
        this.dataProducao=dataProducao; this.idVeiculo=idVeiculo; this.idRequestProducao=idRequestProducao;
    }
    public int getId() { return id; } public int getIdPedido() { return idPedido; }
    public int getIdReceita() { return idReceita; } public double getLitros() { return litros; }
    public LocalDate getDataProducao() { return dataProducao; } public int getIdVeiculo() { return idVeiculo; }
    public int getIdRequestProducao() { return idRequestProducao; }
    // aliases para compatibilidade com screens
    public int getIdpedido() { return idPedido; } public int getIdreceita() { return idReceita; }
    public int getIdveiculo() { return idVeiculo; }
    public void setId(int id) { this.id=id; } public void setIdPedido(int i) { this.idPedido=i; }
    public void setIdReceita(int i) { this.idReceita=i; } public void setLitros(double l) { this.litros=l; }
    public void setDataProducao(LocalDate d) { this.dataProducao=d; } public void setIdVeiculo(int i) { this.idVeiculo=i; }
    public void setIdRequestProducao(int i) { this.idRequestProducao=i; }
}
