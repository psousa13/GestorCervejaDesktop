package model;

import java.time.LocalDateTime;

public class RequestProducao {
    private int idrequestProducao;
    private int idusuario;
    private String estado;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataConclusao;

    public RequestProducao(int idrequestProducao, int idusuario, String estado,
                           LocalDateTime dataCriacao, LocalDateTime dataConclusao) {
        this.idrequestProducao = idrequestProducao;
        this.idusuario         = idusuario;
        this.estado            = estado;
        this.dataCriacao       = dataCriacao;
        this.dataConclusao     = dataConclusao;
    }

    public int getId()                      { return idrequestProducao; }
    public int getIdusuario()               { return idusuario; }
    public String getEstado()               { return estado; }
    public LocalDateTime getDataCriacao()   { return dataCriacao; }
    public LocalDateTime getDataConclusao() { return dataConclusao; }

    public void setEstado(String estado)          { this.estado = estado; }
    public void setDataConclusao(LocalDateTime d)  { this.dataConclusao = d; }

    @Override
    public String toString() {
        return "[" + idrequestProducao + "] Usuario: " + idusuario + " | " + estado + " | " + dataCriacao;
    }
}