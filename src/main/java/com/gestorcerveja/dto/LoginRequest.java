package com.gestorcerveja.dto;
public class LoginRequest {
    private String nome; private String senha;
    public String getNome()  { return nome; }
    public String getSenha() { return senha; }
    public void setNome(String n)  { this.nome = n; }
    public void setSenha(String s) { this.senha = s; }
}
