/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author PICHAU
 */
public class Attribute implements Serializable {
    
    private String sku;
    private String name;
    private String description;
    private String type;
    private Boolean highestPrice;
    private String quantityType;
    private Integer quantity;
    private BigDecimal price;
    @JsonIgnore
    private String companyId;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(Boolean highestPrice) {
        this.highestPrice = highestPrice;
    }

    public String getQuantityType() {
        return quantityType;
    }

    public void setQuantityType(String quantityType) {
        this.quantityType = quantityType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Attribute{" + "sku=" + sku + ", name=" + name + ", description=" + description + ", type=" + type + ", highestPrice=" + highestPrice + ", quantityType=" + quantityType + ", quantity=" + quantity + ", price=" + price + '}';
    }

}