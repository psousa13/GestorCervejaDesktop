package com.gestorcerveja.dto;

import java.time.LocalDate;
import java.util.List;

public class PedidoRequest {
    private LocalDate            dataEstimadaConclusao;
    private CustomerRequest      customer;
    private List<PedidoItemRequest> items;

    public LocalDate               getDataEstimadaConclusao() { return dataEstimadaConclusao; }
    public CustomerRequest         getCustomer()              { return customer; }
    public List<PedidoItemRequest> getItems()                 { return items; }
    public void setDataEstimadaConclusao(LocalDate d) { this.dataEstimadaConclusao = d; }
    public void setCustomer(CustomerRequest c)         { this.customer = c; }
    public void setItems(List<PedidoItemRequest> i)   { this.items = i; }
}
