/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.bean;

import static com.metremobbi.util.Utils.addDetailMessage;
import static com.metremobbi.util.Utils.uploadNew;
import com.metremobbi.model.Product;
import com.metremobbi.enums.CATEGORY;
import com.metremobbi.model.Attribute;
import com.metremobbi.model.Category;
import com.metremobbi.service.ProductService;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.print.attribute.standard.Severity;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.binary.Base64;
import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.event.FileUploadEvent;
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
    private List<Category> categoryList;
    @Getter
    @Setter
    private List<Product> selectedProducts;
    LazyDataModel<Product> productLazy;
    @Getter
    @Setter
    List<Product> filteredValue;
    @Getter
    @Setter
    List<Attribute> atributtes;

    public ProductMB() {
        products = new ArrayList<>();
        product = new Product();
        service = new ProductService();
        atributtes = new ArrayList<>();
        categoryList = new ArrayList<>();
    }

    public void novo() {
        product = new Product();
    }

    @PostConstruct
    public void init() {
        try {
            get();
        } catch (IOException ex) {
            products = new ArrayList();
            categoryList = new ArrayList();
        }
    }

    public void get() throws IOException {
        products = service.getProducts();
        categoryList = service.getCategoryList();
    }

    public void save() throws IOException {
        System.out.println("id produto " + product.getId());
        System.out.println("produto " + product.getName());
        if (product.getId() == null) {
            try {
                service.postProduct(product);
                //products.add(product);
                System.out.println("Produto: " + product.toString() + " salvo com sucesso");
                addDetailMessage("Produto Salvo com sucesso!");
                products.add(product);
                novo();
            } catch (Exception e) {
                e.printStackTrace();
                addDetailMessage("Não foi possível salvar", FacesMessage.SEVERITY_ERROR);
            }
        } else {
            service.putProduct(product);
            System.out.println("Produto: " + product.toString() + " atualizado com sucesso");
            addDetailMessage("Produto atualizado com sucesso!");
            novo();
        }
    }

    public void delete() throws IOException {
        service.deleteProduct(selectedProducts);
        products.removeAll(selectedProducts);
        System.out.println(selectedProducts.size() + " deletados com sucesso!");
        addDetailMessage("Produtos deletados com sucesso!");
        novo();
    }

    public void uploadPhoto(FileUploadEvent event) {
        InputStream finput;
        try {
            finput = event.getFile().getInputstream();
            byte[] imageBytes = new byte[(int) event.getFile().getSize()];
            finput.read(imageBytes, 0, imageBytes.length);
            finput.close();
            String imageStr = Base64.encodeBase64String(imageBytes);
            product.setImageBase64(imageStr);
        } catch (Exception e) {
        }
    }

    public void debug(){
        System.out.println("value Category: " + product.getCategoryMain().getName());
    }
}
