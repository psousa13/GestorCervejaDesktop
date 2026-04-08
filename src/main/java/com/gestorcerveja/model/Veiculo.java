package com.gestorcerveja.model;

public class Veiculo {
    private int idveiculo;
    private String matricula;
    private String marca;
    private String cor;
    private String nome;
    private double capacidade;
    private double ocupacaoAtual;
    private String tipo;

    public Veiculo(int idveiculo, String matricula, String marca, String cor, String nome,
                   double capacidade, double ocupacaoAtual, String tipo) {
        this.idveiculo     = idveiculo;
        this.matricula     = matricula;
        this.marca         = marca;
        this.cor           = cor;
        this.nome          = nome;
        this.capacidade    = capacidade;
        this.ocupacaoAtual = ocupacaoAtual;
        this.tipo          = tipo;
    }

    public int getId()               { return idveiculo; }
    public String getMatricula()     { return matricula; }
    public String getMarca()         { return marca; }
    public String getCor()           { return cor; }
    public String getNome()          { return nome; }
    public double getCapacidade()    { return capacidade; }
    public double getOcupacaoAtual() { return ocupacaoAtual; }
    public String getTipo()          { return tipo; }

    public void setOcupacaoAtual(double v) { this.ocupacaoAtual = v; }
    public void setCor(String cor)         { this.cor = cor; }
    public void setNome(String nome)       { this.nome = nome; }

    @Override
    public String toString() {
        return "[" + idveiculo + "] " + matricula + " | " + marca + " | " + tipo
                + " | " + ocupacaoAtual + "/" + capacidade + "L";
    }
}