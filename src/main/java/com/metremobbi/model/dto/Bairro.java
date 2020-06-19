/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.model.dto;

import java.math.BigDecimal;
import java.util.Objects;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

/**
 *
 * @author Renato
 */
public class Bairro {

    private String bairro;
    private Boolean entrega;
    private BigDecimal taxa;

    public Bairro() {
    }

    public Bairro(String bairro) {
        this.bairro = bairro;
        this.taxa = BigDecimal.ZERO;
    }

    public String getBairro() {
        return bairro;
    }

    public Boolean getEntrega() {
        return entrega;
    }

    public void setEntrega(Boolean entrega) {
        this.entrega = entrega;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public BigDecimal getTaxa() {
        return taxa;
    }

    public void setTaxa(BigDecimal taxa) {
        this.taxa = taxa;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.bairro);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bairro other = (Bairro) obj;
        if (!Objects.equals(this.bairro, other.bairro)) {
            return false;
        }
        return true;
    }

}
