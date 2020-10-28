/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.model;

import com.metremobbi.model.dto.Bairro;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 *
 * @author JOAO PAULO
 */
@Data
public class Company {

    private String id;
    private String name;
    private String socialReason;
    private String phone;
    private String color;
    private String logo;
    private String owner;
    private String domain;
    private String cgccpf;
    private Address address;
    private BigDecimal deliveryCost;
    private TimeOpen time;
    private String funcionamento;
    private String messageWelcome;

    private List<Bairro> bairros;
    private String nameUrl;
    private String aproxTime;
    private Boolean integration;
    private Boolean trial;
    private String trialDate;
    private List<CouponCode> coupons;
    private Boolean uniqueDeliveryCost;
    private Integer licenseType;
    private String licenseDate;
    private Boolean onlyMenu;
    private Boolean freeVersion;
    private Boolean block;
    private Boolean worksCoupon;
    private Boolean deliveryOnly, withdrawalOnly, decimalQuantity;
    private String email;
    private Shift shift;
    private BigDecimal minimalValue;
    private Boolean open;
    private Boolean openForClient;
    private String payLink;
    private Boolean autoAdress;
    private String orderProducts;
    
    private BigDecimal valueMaxPromoDelivery;
    private BigDecimal valuePromoDelivery;

    public String getMessageWelcome() {
        if (messageWelcome == null) {
            messageWelcome = "Para iniciar seu pedido";
        }
        return messageWelcome;
    }

    public Boolean getWorksCoupon() {
        if (worksCoupon == null) {
            worksCoupon = Boolean.FALSE;
        }
        return worksCoupon;
    }

    public List<CouponCode> getCoupons() {
        if (coupons == null) {
            coupons = new ArrayList();
        }
        return coupons;
    }

    public Boolean getUniqueDeliveryCost() {
        if (uniqueDeliveryCost == null) {
            uniqueDeliveryCost = true;
        }
        return uniqueDeliveryCost;
    }

    public Boolean getOnlyMenu() {
        if (onlyMenu == null) {
            onlyMenu = false;
        }
        return onlyMenu;
    }
    
    public Boolean getFreeVersion() {
        if (freeVersion == null) {
            freeVersion = false;
        }
        return freeVersion;
    }

    public Boolean getDeliveryOnly() {
        if (deliveryOnly == null) {
            deliveryOnly = true;
        }
        return deliveryOnly;
    }

    public void setDeliveryOnly(Boolean deliveryOnly) {
        this.deliveryOnly = deliveryOnly;
    }

    public Boolean getWithdrawalOnly() {
        if (withdrawalOnly == null) {
            withdrawalOnly = true;
        }
        return withdrawalOnly;
    }

    public void setWithdrawalOnly(Boolean withdrawalOnly) {
        this.withdrawalOnly = withdrawalOnly;
    }

    public Boolean getDecimalQuantity() {
        if (decimalQuantity == null) {
            decimalQuantity = false;
        }
        return decimalQuantity;
    }

    public void setDecimalQuantity(Boolean decimalQuantity) {
        this.decimalQuantity = decimalQuantity;
    }
    
    

}
