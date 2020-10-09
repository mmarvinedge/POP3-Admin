/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author JOAO PAULO
 */
@JsonInclude(Include.NON_NULL)
public class Product implements Serializable {

    private String id;
    private String sku;
    private String name;
    private Integer order;
    private Boolean enabled;
    private String description;
    private Boolean availability;
    @JsonIgnore
    private String imageType;
    private String imageBase64;
    private Double price;
    private BigDecimal priceOriginal;
    private String companyId;
    private Category categoryMain;
    private List<Category> categories;
    @JsonIgnore
    private List<Attribute> attributes;
    private String printer;
    private Boolean promo;

    private String sizePizza;
    private Integer maxPizza;
    private List<FlavorPizza> flavorsPizza;
    private String rulePricePizza;
    private ProductDay productDay;
    private Shift shift;
    private BigDecimal priceMenu;
    private Boolean onlyMenu;

    public Product() {

    }

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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
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
        if (imageType == null || imageType.isEmpty()) {
            imageType = "image/png";
        }
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

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public Boolean getPromo() {
        return promo;
    }

    public void setPromo(Boolean promo) {
        this.promo = promo;
    }

    public String getSizePizza() {
        return sizePizza;
    }

    public void setSizePizza(String sizePizza) {
        this.sizePizza = sizePizza;
    }

    public Integer getMaxPizza() {
        return maxPizza;
    }

    public void setMaxPizza(Integer maxPizza) {
        this.maxPizza = maxPizza;
    }

    public List<FlavorPizza> getFlavorsPizza() {
        if(flavorsPizza == null) {
            flavorsPizza = new ArrayList();
        }
        return flavorsPizza;
    }

    public void setFlavorsPizza(List<FlavorPizza> flavorsPizza) {
        this.flavorsPizza = flavorsPizza;
    }

    public String getRulePricePizza() {
        return rulePricePizza;
    }

    public void setRulePricePizza(String rulePricePizza) {
        this.rulePricePizza = rulePricePizza;
    }

    public BigDecimal getPriceOriginal() {
        return priceOriginal;
    }

    public void setPriceOriginal(BigDecimal priceOriginal) {
        this.priceOriginal = priceOriginal;
    }

    public BigDecimal getPriceMenu() {
        return priceMenu;
    }

    public void setPriceMenu(BigDecimal priceMenu) {
        this.priceMenu = priceMenu;
    }

    public Boolean getOnlyMenu() {
        return onlyMenu;
    }

    public void setOnlyMenu(Boolean showMenu) {
        this.onlyMenu = showMenu;
    }
    
    public Shift getShift() {
        if (shift == null) {
            return shift = new Shift();
        } else {
            return shift;
        }
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.sku);
        hash = 53 * hash + Objects.hashCode(this.name);
        return hash;
    }

    public ProductDay getProductDay() {
        if (productDay == null) {
            productDay = new ProductDay();
        }
        return productDay;
    }

    public void setProductDay(ProductDay productDay) {
        this.productDay = productDay;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.sku, other.sku)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", sku=" + sku + ", name=" + name + '}';
    }

}
