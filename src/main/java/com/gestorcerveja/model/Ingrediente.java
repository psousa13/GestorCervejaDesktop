package com.gestorcerveja.model;
public class Ingrediente {
    private int id; private String nome; private String unidade;
    private double stockAtual; private double stockMinimo;
    public Ingrediente() {}
    public Ingrediente(int id, String nome, String unidade, double stockAtual, double stockMinimo) {
        this.id=id; this.nome=nome; this.unidade=unidade; this.stockAtual=stockAtual; this.stockMinimo=stockMinimo;
    }
    public int getId() { return id; } public String getNome() { return nome; }
    public String getUnidade() { return unidade; } public double getStockAtual() { return stockAtual; }
    public double getStockMinimo() { return stockMinimo; }
    public void setId(int id) { this.id=id; } public void setNome(String n) { this.nome=n; }
    public void setUnidade(String u) { this.unidade=u; } public void setStockAtual(double s) { this.stockAtual=s; }
    public void setStockMinimo(double s) { this.stockMinimo=s; }
}
