package com.gestorcerveja.dto;

import java.time.LocalDate;

public class PedidoResponse {
    private int       id;
    private String    numeroPedido;
    private int       idcliente;
    private LocalDate dataPedido;
    private String    estado;
    private LocalDate dataEstimadaConclusao;

    // Nested objects for the frontend
    private CustomerInfo customer;
    private TotalsInfo   totals;

    public PedidoResponse(int id, int idcliente, LocalDate dataPedido,
                          String estado, LocalDate dataEstimadaConclusao) {
        this.id = id;
        this.numeroPedido = String.format("PED-%03d", id);
        this.idcliente = idcliente;
        this.dataPedido = dataPedido;
        this.estado = estado;
        this.dataEstimadaConclusao = dataEstimadaConclusao;
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public int          getId()                    { return id; }
    public String       getNumeroPedido()           { return numeroPedido; }
    public int          getIdcliente()              { return idcliente; }
    public LocalDate    getDataPedido()             { return dataPedido; }
    public String       getEstado()                 { return estado; }
    public LocalDate    getDataEstimadaConclusao()  { return dataEstimadaConclusao; }
    public CustomerInfo getCustomer()               { return customer; }
    public TotalsInfo   getTotals()                 { return totals; }

    // ── Setters ───────────────────────────────────────────────────────────────
    public void setCustomer(CustomerInfo c) { this.customer = c; }
    public void setTotals(TotalsInfo t)     { this.totals = t; }

    // ── Nested DTOs ───────────────────────────────────────────────────────────
    public static class CustomerInfo {
        private String tipoCliente;
        private String nome;
        private String email;
        private String telefone;

        public CustomerInfo(String tipoCliente, String nome, String email, String telefone) {
            this.tipoCliente = tipoCliente;
            this.nome = nome;
            this.email = email;
            this.telefone = telefone;
        }
        public String getTipoCliente() { return tipoCliente; }
        public String getNome()        { return nome; }
        public String getEmail()       { return email; }
        public String getTelefone()    { return telefone; }
    }

    public static class TotalsInfo {
        private double totalValor;
        private int    totalGrades;
        private double totalLitros;

        public TotalsInfo(double totalValor, int totalGrades, double totalLitros) {
            this.totalValor = totalValor;
            this.totalGrades = totalGrades;
            this.totalLitros = totalLitros;
        }
        public double getTotalValor()  { return totalValor; }
        public int    getTotalGrades() { return totalGrades; }
        public double getTotalLitros() { return totalLitros; }
    }
}
