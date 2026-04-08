package com.gestorcerveja.model;

import java.time.LocalDate;

public class ClienteParticular extends Cliente {
    private String nomeCompleto;
    private String nif;

    public ClienteParticular(int idcliente, String email, String telefone, LocalDate dataRegisto,
                             String nomeCompleto, String nif) {
        super(idcliente, "Particular", email, telefone, dataRegisto);
        this.nomeCompleto = nomeCompleto;
        this.nif          = nif;
    }

    public String getNomeCompleto() { return nomeCompleto; }
    public String getNif()          { return nif; }

    public void setNomeCompleto(String v) { this.nomeCompleto = v; }
    public void setNif(String nif)        { this.nif = nif; }

    @Override
    public String toString() { return super.toString() + " | " + nomeCompleto + " | NIF: " + nif; }
}