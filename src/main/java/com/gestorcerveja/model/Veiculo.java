package com.gestorcerveja.model;
public class Veiculo {
    private int id; private String matricula; private String marca; private String cor;
    private String nome; private double capacidade; private double ocupacaoAtual; private String tipo;
    public Veiculo() {}
    public Veiculo(int id, String matricula, String marca, String cor, String nome, double capacidade, double ocupacaoAtual, String tipo) {
        this.id=id; this.matricula=matricula; this.marca=marca; this.cor=cor; this.nome=nome;
        this.capacidade=capacidade; this.ocupacaoAtual=ocupacaoAtual; this.tipo=tipo;
    }
    public int getId() { return id; } public String getMatricula() { return matricula; }
    public String getMarca() { return marca; } public String getCor() { return cor; }
    public String getNome() { return nome; } public double getCapacidade() { return capacidade; }
    public double getOcupacaoAtual() { return ocupacaoAtual; } public String getTipo() { return tipo; }
    public void setId(int id) { this.id=id; } public void setMatricula(String m) { this.matricula=m; }
    public void setMarca(String m) { this.marca=m; } public void setCor(String c) { this.cor=c; }
    public void setNome(String n) { this.nome=n; } public void setCapacidade(double c) { this.capacidade=c; }
    public void setOcupacaoAtual(double o) { this.ocupacaoAtual=o; } public void setTipo(String t) { this.tipo=t; }
}
