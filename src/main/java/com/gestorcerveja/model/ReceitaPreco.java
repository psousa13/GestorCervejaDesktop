package com.gestorcerveja.model;
import java.time.LocalDate;
public class ReceitaPreco {
    private int id; private int idReceita; private double precoPorLitro;
    private LocalDate dataFim;
    public ReceitaPreco() {}
    public ReceitaPreco(int id, int idReceita, double precoPorLitro, LocalDate dataFim) {
        this.id=id; this.idReceita=idReceita; this.precoPorLitro=precoPorLitro; this.dataFim=dataFim;
    }
    public int getId() { return id; } public int getIdReceita() { return idReceita; }
    public double getPrecoPorLitro() { return precoPorLitro; }
    public LocalDate getDataFim() { return dataFim; }
    public void setId(int id) { this.id=id; } public void setIdReceita(int i) { this.idReceita=i; }
    public void setPrecoPorLitro(double p) { this.precoPorLitro=p; } 
    public void setDataFim(LocalDate d) { this.dataFim=d; }
}
