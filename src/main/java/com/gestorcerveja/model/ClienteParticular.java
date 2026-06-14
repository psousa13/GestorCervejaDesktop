package com.gestorcerveja.model;
import java.time.LocalDate;
public class ClienteParticular extends Cliente {
    private String nomeCompleto; private String nif;
    public ClienteParticular() {}
    public ClienteParticular(int id, String email, String telefone, LocalDate dataRegisto, String nomeCompleto, String nif) {
        super(id, "Particular", email, telefone, dataRegisto);
        this.nomeCompleto=nomeCompleto; this.nif=nif;
    }
    public String getNomeCompleto() { return nomeCompleto; } public String getNif() { return nif; }
    public void setNomeCompleto(String n) { this.nomeCompleto=n; } public void setNif(String n) { this.nif=n; }
}
