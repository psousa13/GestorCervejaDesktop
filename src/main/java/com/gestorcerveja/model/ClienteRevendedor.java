package com.gestorcerveja.model;
import java.time.LocalDate;
public class ClienteRevendedor extends Cliente {
    private String nomeEmpresa; private String vatEmpresa; private String contactoPrincipal;
    private String departamento; private String telefoneEmpresa; private String notaInterna;
    public ClienteRevendedor() {}
    public ClienteRevendedor(int id, String email, String telefone, LocalDate dataRegisto, String nomeEmpresa,
                             String vatEmpresa, String contactoPrincipal, String departamento,
                             String telefoneEmpresa, String notaInterna) {
        super(id, "Revendedor", email, telefone, dataRegisto);
        this.nomeEmpresa=nomeEmpresa; this.vatEmpresa=vatEmpresa; this.contactoPrincipal=contactoPrincipal;
        this.departamento=departamento; this.telefoneEmpresa=telefoneEmpresa; this.notaInterna=notaInterna;
    }
    public String getNomeEmpresa() { return nomeEmpresa; } public String getVatEmpresa() { return vatEmpresa; }
    public String getContactoPrincipal() { return contactoPrincipal; } public String getDepartamento() { return departamento; }
    public String getTelefoneEmpresa() { return telefoneEmpresa; } public String getNotaInterna() { return notaInterna; }
    public void setNomeEmpresa(String n) { this.nomeEmpresa=n; } public void setVatEmpresa(String v) { this.vatEmpresa=v; }
    public void setContactoPrincipal(String c) { this.contactoPrincipal=c; } public void setDepartamento(String d) { this.departamento=d; }
    public void setTelefoneEmpresa(String t) { this.telefoneEmpresa=t; } public void setNotaInterna(String n) { this.notaInterna=n; }
}
