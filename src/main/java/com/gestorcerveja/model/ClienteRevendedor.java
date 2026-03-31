package model;

import java.time.LocalDate;

public class ClienteRevendedor extends Cliente {
    private String nomeEmpresa;
    private String vatEmpresa;
    private String contactoPrincipal;
    private String departamento;
    private String telefoneEmpresa;
    private String notaInterna;

    public ClienteRevendedor(int idcliente, String email, String telefone, LocalDate dataRegisto,
                             String nomeEmpresa, String vatEmpresa, String contactoPrincipal,
                             String departamento, String telefoneEmpresa, String notaInterna) {
        super(idcliente, "Revendedor", email, telefone, dataRegisto);
        this.nomeEmpresa       = nomeEmpresa;
        this.vatEmpresa        = vatEmpresa;
        this.contactoPrincipal = contactoPrincipal;
        this.departamento      = departamento;
        this.telefoneEmpresa   = telefoneEmpresa;
        this.notaInterna       = notaInterna;
    }

    public String getNomeEmpresa()       { return nomeEmpresa; }
    public String getVatEmpresa()        { return vatEmpresa; }
    public String getContactoPrincipal() { return contactoPrincipal; }
    public String getDepartamento()      { return departamento; }
    public String getTelefoneEmpresa()   { return telefoneEmpresa; }
    public String getNotaInterna()       { return notaInterna; }

    public void setNomeEmpresa(String v)       { this.nomeEmpresa = v; }
    public void setVatEmpresa(String v)        { this.vatEmpresa = v; }
    public void setContactoPrincipal(String v) { this.contactoPrincipal = v; }
    public void setDepartamento(String v)      { this.departamento = v; }
    public void setTelefoneEmpresa(String v)   { this.telefoneEmpresa = v; }
    public void setNotaInterna(String v)       { this.notaInterna = v; }

    @Override
    public String toString() { return super.toString() + " | " + nomeEmpresa + " | VAT: " + vatEmpresa; }
}