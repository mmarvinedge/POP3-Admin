/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.enums;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author PICHAU
 */
public enum CATEGORY {
    
    LUNCH("Almoço"),
    SNACK("Lanche"),
    ICE("Sorvete"),
    DRINKS("Bebidas");

    @Setter
    @Getter
    private final String descrition;

    CATEGORY(String descrition) {
        this.descrition = descrition;
    }
}
