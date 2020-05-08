/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Márvin Edge
 */
@Getter
@Setter
public class RankingProduct {
    
    private String product;
    private Double qnt;
    private Double total;
    
    public RankingProduct(Object[] o) {
        this.product = o[0].toString();
        this.qnt = (Double) o[1];
        this.total = (Double) o[2];
    }
    
}
