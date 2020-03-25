/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.bean;

import com.metremobbi.enums.CATEGORY;
import com.metremobbi.infra.model.Filter;
import com.metremobbi.model.Car;
import com.metremobbi.model.Product;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import javax.inject.Named;

/**
 *
 * @author JOAO PAULO
 */
@ManagedBean
@ViewScoped
public class ProductMB implements Serializable {

    @Getter
    @Setter
    private Product product;
    @Getter
    @Setter
    private List<Product> products;
    @Getter
    @Setter
    private List<Product> selectedProducts;
    LazyDataModel<Product> productLazy;
    
    public ProductMB() {
        products = new ArrayList<>();
    }
    public void novo(){
        product = new Product();
    }
    
    @PostConstruct
    public void init(){
        addProdutosTestes();
       
    }

    public void addProdutosTestes() {
        novo();
        Product p1 = new Product();
        p1.setName("produto 1");
        p1.setCompanyId("1");
        p1.setPrice(15.5);
        p1.setSku("01");
        p1.setCategory(CATEGORY.ICE);
        products.add(p1);
        Product p2 = new Product();
        p2.setName("produto 2");
        p2.setCompanyId("1");
        p2.setPrice(9.5);
        p2.setSku("02");
        p2.setCategory(CATEGORY.DRINKS);
        products.add(p2);
        System.out.println("adicionou: " + products.size() + " produtos na lista");
    }
    
    
    public void save(){
        System.out.println("Produto: " + product.toString() + " salvo com sucesso");
        novo();
    }
    
    public void delete(){
        System.out.println("Produto: " + product.toString() + " deletado com sucesso");
        novo();
    }
    
}
