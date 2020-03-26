/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

/**
 *
 * @author PICHAU
 */
public class Category implements Serializable {
    
    private String id;
    private String sku;
    private String name;
    @JsonIgnore
    private String order;
    private Boolean enable;
    private String description;
    @JsonIgnore
    private String companyId;
    private Boolean main;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getMain() {
        return main;
    }

    public void setMain(Boolean main) {
        this.main = main;
    }
    
    @Override
    public String toString() {
        return "Category{" + "id=" + id + ", sku=" + sku + ", name=" + name + ", enable=" + enable + ", description=" + description + '}';
    }
    
}
