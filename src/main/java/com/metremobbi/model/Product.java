/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.metremobbi.model.Category;
import com.metremobbi.model.Attribute;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author JOAO PAULO
 */
@JsonInclude(Include.NON_NULL)
public class Product implements Serializable {

    private String id;
    private String sku;
    private String name;
    @JsonIgnore
    private String order;
    private Boolean enable;
    private String description;
    private Boolean availability;
    @JsonIgnore
    private String imageType;
    @JsonIgnore
    private String imageBase64;
    private Double price;
    private String companyId;
    private Category categoryMain;
    private List<Category> categories;
    private List<Attribute> attributes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Category getCategoryMain() {
        return categoryMain;
    }

    public void setCategoryMain(Category categoryMain) {
        this.categoryMain = categoryMain;
    }
    
    @Override
    public String toString() {
        return "Product{" + " sku=" + sku + ", name=" + name + ", order=" + order + ", enable=" + enable + ", description=" + description + ", availability=" + availability + ", imageType=" + imageType + ", imageBase64=" + imageBase64 + ", price=" + price + ", companyId=" + companyId + ", attributes=" + attributes + '}';
    }

}
