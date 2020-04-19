/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.model;

import java.math.BigDecimal;

/**
 *
 * @author JOAO PAULO
 */
public class Company {

    private int id;
    private String companyName;
    private String phone;
    private String owner;
    private Address address;
    private BigDecimal delivertCost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public BigDecimal getDelivertCost() {
        return delivertCost;
    }

    public void setDelivertCost(BigDecimal delivertCost) {
        this.delivertCost = delivertCost;
    }

}
