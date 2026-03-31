package model;

import java.time.LocalDate;

public class Cliente {
    private int idcliente;
    private String tipoCliente;
    private String email;
    private String telefone;
    private LocalDate dataRegisto;

    public Cliente(int idcliente, String tipoCliente, String email, String telefone, LocalDate dataRegisto) {
        this.idcliente   = idcliente;
        this.tipoCliente = tipoCliente;
        this.email       = email;
        this.telefone    = telefone;
        this.dataRegisto = dataRegisto;
    }

    public int getId()               { return idcliente; }
    public String getTipoCliente()   { return tipoCliente; }
    public String getEmail()         { return email; }
    public String getTelefone()      { return telefone; }
    public LocalDate getDataRegisto(){ return dataRegisto; }

    public void setEmail(String email)        { this.email = email; }
    public void setTelefone(String telefone)  { this.telefone = telefone; }
    public void setTipoCliente(String tipo)   { this.tipoCliente = tipo; }

    @Override
    public String toString() {
        return "[" + idcliente + "] " + tipoCliente + " | " + email + " | " + telefone;
    }
}