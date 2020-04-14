/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.metremobbi.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Márvin
 */
public class AttributeValue implements Serializable {

    private String id;
    private String sku;
    private String attribute_sku;
    private String name;
    private String description;
    private BigDecimal price;

}
