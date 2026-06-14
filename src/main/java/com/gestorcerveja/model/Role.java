package com.gestorcerveja.model;
public class Role {
    private int id; private String nome;
    public Role() {}
    public Role(int id, String nome) { this.id = id; this.nome = nome; }
    public int getId() { return id; } public String getNome() { return nome; }
    public void setId(int id) { this.id = id; } public void setNome(String n) { this.nome = n; }
}
