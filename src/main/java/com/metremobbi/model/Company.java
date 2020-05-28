/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.model;

import com.metremobbi.model.dto.Bairro;
import java.math.BigDecimal;
import java.util.Date;
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
    private Address address;
    private BigDecimal deliveryCost;
    private TimeOpen time;
    private String funcionamento;
    private String messageWelcome;
    private List<Bairro> bairros;
    private String nameUrl;
    private Boolean integration;
    private Boolean trial;
    private String trialDate;
    private String aproxTime;

    public String getMessageWelcome() {
        if (messageWelcome == null) {
            messageWelcome = "Para iniciar seu pedido";
        }
        return messageWelcome;
    }

}
