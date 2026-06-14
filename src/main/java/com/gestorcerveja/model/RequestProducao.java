package com.gestorcerveja.model;
import java.time.LocalDateTime;
public class RequestProducao {
    private int id; private int idUsuario; private String estado;
    private LocalDateTime dataCriacao; private LocalDateTime dataConclusao;
    public RequestProducao() {}
    public RequestProducao(int id, int idUsuario, String estado, LocalDateTime dataCriacao, LocalDateTime dataConclusao) {
        this.id=id; this.idUsuario=idUsuario; this.estado=estado;
        this.dataCriacao=dataCriacao; this.dataConclusao=dataConclusao;
    }
    public int getId() { return id; } public int getIdusuario() { return idUsuario; }
    public String getEstado() { return estado; } public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataConclusao() { return dataConclusao; }
    public void setId(int id) { this.id=id; } public void setIdusuario(int i) { this.idUsuario=i; }
    public void setEstado(String e) { this.estado=e; } public void setDataCriacao(LocalDateTime d) { this.dataCriacao=d; }
    public void setDataConclusao(LocalDateTime d) { this.dataConclusao=d; }
}
