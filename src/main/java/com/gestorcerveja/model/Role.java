package model;

public class Role {
    private int idrole;
    private String nome;
    private String descricao;

    public Role(int idrole, String nome, String descricao) {
        this.idrole    = idrole;
        this.nome      = nome;
        this.descricao = descricao;
    }

    public int getId()           { return idrole; }
    public String getNome()      { return nome; }
    public String getDescricao() { return descricao; }

    public void setNome(String nome)           { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Override
    public String toString() { return "[" + idrole + "] " + nome; }
}