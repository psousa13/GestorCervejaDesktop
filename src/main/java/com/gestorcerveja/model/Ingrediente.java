package com.gestorcerveja.model;

public class Ingrediente {
    private int idingrediente;
    private String nome;
    private String unidade;
    private double stockAtual;
    private double stockMinimo;

    public Ingrediente(int idingrediente, String nome, String unidade, double stockAtual, double stockMinimo) {
        this.idingrediente = idingrediente;
        this.nome          = nome;
        this.unidade       = unidade;
        this.stockAtual    = stockAtual;
        this.stockMinimo   = stockMinimo;
    }

    public int getId()             { return idingrediente; }
    public String getNome()        { return nome; }
    public String getUnidade()     { return unidade; }
    public double getStockAtual()  { return stockAtual; }
    public double getStockMinimo() { return stockMinimo; }

    public void setNome(String nome)             { this.nome = nome; }
    public void setUnidade(String unidade)       { this.unidade = unidade; }
    public void setStockAtual(double stockAtual) { this.stockAtual = stockAtual; }
    public void setStockMinimo(double v)         { this.stockMinimo = v; }

    @Override
    public String toString() {
        return "[" + idingrediente + "] " + nome + " (" + unidade + ") stock: " + stockAtual;
    }
}