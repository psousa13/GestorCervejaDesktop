package com.gestorcerveja.model;

public class Receita {
    private int idreceita;
    private String nome;
    private String descricao;

    public Receita(int idreceita, String nome, String descricao) {
        this.idreceita = idreceita;
        this.nome      = nome;
        this.descricao = descricao;
    }

    public int getId()           { return idreceita; }
    public String getNome()      { return nome; }
    public String getDescricao() { return descricao; }

    public void setNome(String nome)           { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Override
    public String toString() { return "[" + idreceita + "] " + nome; }
}