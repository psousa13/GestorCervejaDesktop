package com.gestorcerveja.model;
public class Receita {
    private int id; private String nome; private String descricao;
    public Receita() {}
    public Receita(int id, String nome, String descricao) { this.id=id; this.nome=nome; this.descricao=descricao; }
    public int getId() { return id; } public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public void setId(int id) { this.id=id; } public void setNome(String n) { this.nome=n; }
    public void setDescricao(String d) { this.descricao=d; }
}
