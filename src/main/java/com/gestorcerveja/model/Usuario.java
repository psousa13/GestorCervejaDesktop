package com.gestorcerveja.model;
public class Usuario {
    private int id; private String nome; private String senha; private int idrole;
    public Usuario() {}
    public Usuario(int id, String nome, String senha, int idrole) {
        this.id=id; this.nome=nome; this.senha=senha; this.idrole=idrole;
    }
    public int getId() { return id; } public String getNome() { return nome; }
    public String getSenha() { return senha; } public int getIdrole() { return idrole; }
    public void setId(int id) { this.id=id; } public void setNome(String n) { this.nome=n; }
    public void setSenha(String s) { this.senha=s; } public void setIdrole(int r) { this.idrole=r; }
}
