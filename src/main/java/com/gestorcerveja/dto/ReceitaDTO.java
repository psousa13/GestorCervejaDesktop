package com.gestorcerveja.dto;

public class ReceitaDTO {
    private int    id;
    private String nome;
    private String descricao;
    private double precoPorLitro;
    private double precoGrade; // preço por grade (11L)

    public ReceitaDTO(int id, String nome, String descricao, double precoPorLitro) {
        this.id = id; this.nome = nome; this.descricao = descricao;
        this.precoPorLitro = precoPorLitro;
        this.precoGrade = Math.round(precoPorLitro * 11 * 100.0) / 100.0;
    }

    public int    getId()           { return id; }
    public String getNome()         { return nome; }
    public String getDescricao()    { return descricao; }
    public double getPrecoPorLitro(){ return precoPorLitro; }
    public double getPrecoGrade()   { return precoGrade; }
}
