/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.model;

import com.metremobbi.model.dto.Bairro;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author JOAO PAULO
 */
public class Company {

    private String id;
    private String name;
    private String socialReason;
    private String phone;
    private String color;
    private String logo;
    private String owner;
    private Address address;
    private BigDecimal deliveryCost;
    private TimeOpen time;
    private String funcionamento;
    private String messageWelcome;
    private List<Bairro> bairros;
    private String nameUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSocialReason() {
        return socialReason;
    }

    public void setSocialReason(String socialReason) {
        this.socialReason = socialReason;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public TimeOpen getTime() {
        return time;
    }

    public void setTime(TimeOpen time) {
        this.time = time;
    }

    public String getFuncionamento() {
        return funcionamento;
    }

    public void setFuncionamento(String funcionamento) {
        this.funcionamento = funcionamento;
    }

    public String getMessageWelcome() {
        if (messageWelcome == null) {
            messageWelcome = "Para iniciar seu pedido";
        }
        return messageWelcome;
    }

    public void setMessageWelcome(String messageWelcome) {
        this.messageWelcome = messageWelcome;
    }

    public List<Bairro> getBairros() {
        return bairros;
    }

    public void setBairros(List<Bairro> bairros) {
        this.bairros = bairros;
    }

    public String getNameUrl() {
        return nameUrl;
    }

    public void setNameUrl(String nameUrl) {
        this.nameUrl = nameUrl;
    }

}
