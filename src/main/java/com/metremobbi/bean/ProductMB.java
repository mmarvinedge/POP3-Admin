/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.bean;

import static com.metremobbi.util.Utils.addDetailMessage;
import com.metremobbi.model.Product;
import com.metremobbi.enums.CATEGORY;
import com.metremobbi.service.ProductService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.print.attribute.standard.Severity;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.model.LazyDataModel;

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
    private ProductService service;
    @Getter
    @Setter
    private List<Product> products;
    @Getter
    @Setter
    private List<Product> selectedProducts;
    LazyDataModel<Product> productLazy;
    @Getter
    @Setter
    List<Product> filteredValue;

    public ProductMB() {
        products = new ArrayList<>();
    }

    public void novo() {
        product = new Product();
    }

    @PostConstruct
    public void init() {
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

    public void save() {
        System.out.println("produto "+ product.getName());
        try {
            service.postProduct(product);
            //products.add(product);
            System.out.println("Produto: " + product.toString() + " salvo com sucesso");
            addDetailMessage("Produto Salvo com sucesso!");
            novo();
        } catch (Exception e){
            e.printStackTrace();
            addDetailMessage("Não foi possível salvar", FacesMessage.SEVERITY_ERROR);
        }

    }

    public void delete() {
        products.removeAll(selectedProducts);
        System.out.println(selectedProducts.size() + " deletados com sucesso!");
        addDetailMessage("Produtos deletado com sucesso!");
        novo();
    }

}
