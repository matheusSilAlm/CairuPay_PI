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
public class Dividas {
    private int idDivida;
    private Clientes credor;   // Chave estrangeira para o Cliente
    private Clientes devedor;  // Chave estrangeira para o Cliente
    private BigDecimal valorDivida;
    private Date dataAtualizacao; // Correspondente a DATE no SQL

    public Dividas(int idDivida, Clientes credor, Clientes devedor, BigDecimal valorDivida, Date dataAtualizacao) {
        this.idDivida = idDivida;
        this.credor = credor;
        this.devedor = devedor;
        this.valorDivida = valorDivida;
        this.dataAtualizacao = dataAtualizacao;
    }

    public int getIdDivida() {
        return idDivida;
    }

    public Clientes getCredor() {
        return credor;
    }

    public Clientes getDevedor() {
        return devedor;
    }

    public BigDecimal getValorDivida() {
        return valorDivida;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setIdDivida(int idDivida) {
        this.idDivida = idDivida;
    }

    public void setCredor(Clientes credor) {
        this.credor = credor;
    }

    public void setDevedor(Clientes devedor) {
        this.devedor = devedor;
    }

    public void setValorDivida(BigDecimal valorDivida) {
        this.valorDivida = valorDivida;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

   
}

