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
    private Boolean makeDelivery;
    private Boolean makeWithdrawal;

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
        if(uniqueDeliveryCost == null){
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
    
    public Boolean getMakeDelivery(){
        if (makeDelivery == null) {
            makeDelivery = true;
        }
        return makeDelivery;
    }
    
    public Boolean getMakeWithdrawal() {
        if (makeWithdrawal == null) {
            makeWithdrawal = true;
        }
        return makeWithdrawal;
    }

}
