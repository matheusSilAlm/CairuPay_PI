/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.projetoPI.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author teu_s
 */
public class Pagamentos {
    private int idPagamento;
    private Dividas divida; // Chave estrangeira para a Divida
    private Date dataPagamento;
    private BigDecimal valorPago;

    public Pagamentos() {
    }
    
    public Pagamentos(int idPagamento, Dividas divida, Date dataPagamento, BigDecimal valorPago) {
        this.idPagamento = idPagamento;
        this.divida = divida;
        this.dataPagamento = dataPagamento;
        this.valorPago = valorPago;
    }

    public int getIdPagamento() {
        return idPagamento;
    }

    public Dividas getDivida() {
        return divida;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setIdPagamento(int idPagamento) {
        this.idPagamento = idPagamento;
    }

    public void setDivida(Dividas divida) {
        this.divida = divida;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }
    
    
    

}
