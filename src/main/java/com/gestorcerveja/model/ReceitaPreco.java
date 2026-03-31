package model;

import java.time.LocalDate;

public class ReceitaPreco {
    private int idpreco;
    private int idreceita;
    private double precoPorLitro;
    private LocalDate dataVigencia;

    public ReceitaPreco(int idpreco, int idreceita, double precoPorLitro, LocalDate dataVigencia) {
        this.idpreco       = idpreco;
        this.idreceita     = idreceita;
        this.precoPorLitro = precoPorLitro;
        this.dataVigencia  = dataVigencia;
    }

    public int getId()                 { return idpreco; }
    public int getIdreceita()          { return idreceita; }
    public double getPrecoPorLitro()   { return precoPorLitro; }
    public LocalDate getDataVigencia() { return dataVigencia; }

    public void setPrecoPorLitro(double v)      { this.precoPorLitro = v; }
    public void setDataVigencia(LocalDate d)    { this.dataVigencia = d; }

    @Override
    public String toString() {
        return "[" + idpreco + "] Receita: " + idreceita + " | " + precoPorLitro + "€/L desde " + dataVigencia;
    }
}