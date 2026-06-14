package com.gestorcerveja.dto;

public class CustomerRequest {
    private String type; // "Particular" | "Revendedor"
    private String name;
    private String email;
    private String phone;
    private String nif;
    private String empresa;

    public String getType()    { return type; }
    public String getName()    { return name; }
    public String getEmail()   { return email; }
    public String getPhone()   { return phone; }
    public String getNif()     { return nif; }
    public String getEmpresa() { return empresa; }
    public void   setType(String t)    { this.type = t; }
    public void   setName(String n)    { this.name = n; }
    public void   setEmail(String e)   { this.email = e; }
    public void   setPhone(String p)   { this.phone = p; }
    public void   setNif(String n)     { this.nif = n; }
    public void   setEmpresa(String e) { this.empresa = e; }
}
