/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.model;

import java.math.BigDecimal;
import lombok.Data;

/**
 *
 * @author Marvin
 */
@Data
public class CouponCode {
    
    private String id;
    private String slug;
    private Integer quantity;
    private BigDecimal discount;
    private Boolean enable;
    
    public CouponCode() {
    }

}
