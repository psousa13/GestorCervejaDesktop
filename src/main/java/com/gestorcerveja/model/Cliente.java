package com.gestorcerveja.model;
import java.time.LocalDate;
public class Cliente {
    private int id; private String tipoCliente; private String email;
    private String telefone; private LocalDate dataRegisto;
    public Cliente() {}
    public Cliente(int id, String tipoCliente, String email, String telefone, LocalDate dataRegisto) {
        this.id=id; this.tipoCliente=tipoCliente; this.email=email; this.telefone=telefone; this.dataRegisto=dataRegisto;
    }
    public int getId() { return id; } public String getTipoCliente() { return tipoCliente; }
    public String getEmail() { return email; } public String getTelefone() { return telefone; }
    public LocalDate getDataRegisto() { return dataRegisto; }
    public void setId(int id) { this.id=id; } public void setTipoCliente(String t) { this.tipoCliente=t; }
    public void setEmail(String e) { this.email=e; } public void setTelefone(String t) { this.telefone=t; }
    public void setDataRegisto(LocalDate d) { this.dataRegisto=d; }
}
