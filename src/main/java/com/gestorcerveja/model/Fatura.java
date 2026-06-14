package com.gestorcerveja.model;
import java.time.LocalDate;
public class Fatura {
    private int id; private int idPedido; private double valorTotal;
    private LocalDate dataEmissao; private String estado;
    public Fatura() {}
    public Fatura(int id, int idPedido, double valorTotal, LocalDate dataEmissao, String estado) {
        this.id=id; this.idPedido=idPedido; this.valorTotal=valorTotal;
        this.dataEmissao=dataEmissao; this.estado=estado;
    }
    public int getId() { return id; } public int getIdPedido() { return idPedido; }
    public double getValorTotal() { return valorTotal; } public LocalDate getDataEmissao() { return dataEmissao; }
    public String getEstado() { return estado; }
    public void setId(int id) { this.id=id; } public void setIdPedido(int i) { this.idPedido=i; }
    public void setValorTotal(double v) { this.valorTotal=v; } public void setDataEmissao(LocalDate d) { this.dataEmissao=d; }
    public void setEstado(String e) { this.estado=e; }
}
