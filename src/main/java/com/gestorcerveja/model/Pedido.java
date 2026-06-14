package com.gestorcerveja.model;
import java.time.LocalDate;
public class Pedido {
    private int id; private int idcliente; private LocalDate dataPedido;
    private String estado; private LocalDate dataEstimadaConclusao;
    public Pedido() {}
    public Pedido(int id, int idcliente, LocalDate dataPedido, String estado, LocalDate dataEstimadaConclusao) {
        this.id=id; this.idcliente=idcliente; this.dataPedido=dataPedido;
        this.estado=estado; this.dataEstimadaConclusao=dataEstimadaConclusao;
    }
    public int getId() { return id; } public int getIdcliente() { return idcliente; }
    public LocalDate getDataPedido() { return dataPedido; } public String getEstado() { return estado; }
    public LocalDate getDataEstimadaConclusao() { return dataEstimadaConclusao; }
    public void setId(int id) { this.id=id; } public void setIdcliente(int i) { this.idcliente=i; }
    public void setDataPedido(LocalDate d) { this.dataPedido=d; } public void setEstado(String e) { this.estado=e; }
    public void setDataEstimadaConclusao(LocalDate d) { this.dataEstimadaConclusao=d; }
}
