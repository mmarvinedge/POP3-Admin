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
    private String cgccpf;
    private Address address;
    private BigDecimal deliveryCost = BigDecimal.ZERO;
    private TimeOpen time;
    private String funcionamento;
    private String messageWelcome;
    private List<Bairro> bairros;
    private String nameUrl;
    private Boolean integration;
    private Boolean trial;
    private String trialDate;
    private String aproxTime;
    private List<CouponCode> coupons;
    private Boolean worksCoupon;
    private Integer licenseType;
    private String licenseDate;
    private Boolean uniqueDeliveryCost;
    private Boolean onlyMenu;
    private Boolean freeVersion;
    private Boolean deliveryOnly, withdrawalOnly, decimalQuantity;
    private Shift shift;

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
