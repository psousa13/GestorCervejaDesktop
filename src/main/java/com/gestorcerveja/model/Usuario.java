package model;

public class Usuario {
    private int idusuario;
    private String nome;
    private String senha;
    private int idrole;

    public Usuario(int idusuario, String nome, String senha, int idrole) {
        this.idusuario = idusuario;
        this.nome      = nome;
        this.senha     = senha;
        this.idrole    = idrole;
    }

    public int getId()       { return idusuario; }
    public String getNome()  { return nome; }
    public String getSenha() { return senha; }
    public int getIdrole()   { return idrole; }

    public void setNome(String nome)   { this.nome = nome; }
    public void setSenha(String senha) { this.senha = senha; }
    public void setIdrole(int idrole)  { this.idrole = idrole; }

    @Override
    public String toString() { return "[" + idusuario + "] " + nome + " (role: " + idrole + ")"; }
}