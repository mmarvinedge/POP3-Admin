/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.bean;

import static com.metremobbi.util.Utils.addDetailMessage;
import com.metremobbi.model.Product;
import com.metremobbi.model.Attribute;
import com.metremobbi.model.AttributeValue;
import com.metremobbi.model.Category;
import com.metremobbi.service.AttributeService;
import com.metremobbi.service.ProductService;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.binary.Base64;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author JOAO PAULO
 */
@ManagedBean(eager = true)
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
    @Getter
    @Setter
    private String type;
    @Getter
    @Setter
    private Attribute attribute;
    @Getter
    @Setter
    private AttributeService attService;

    public ProductMB() {
        products = new ArrayList<>();
        product = new Product();
        product.setCategoryMain(new Category());
        service = new ProductService();
        atributtes = new ArrayList<>();
        categoryList = new ArrayList<>();
        type = "normal";
        attribute = new Attribute();
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
        System.out.println("CATEGORIAS: " + Arrays.toString(categoryList.toArray()));
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

    public void debug() {
        System.out.println("value Category: " + product.getCategoryMain().getName());
    }

    public void addAttribute() throws IOException {
        attService = new AttributeService();
        if (product.getAttributes() == null) {
            product.setAttributes(new ArrayList());
        }
        
        if (type.equalsIgnoreCase("normal") && product.getAttributes().size() == 0) {
            attribute.setName("Adicionais");
            attribute.setDescription("Complementos adicionais");
            attribute.setQuantity(4);
            attribute.setQuantityType("max");
            attribute.setType("multiple selection");
            attribute.setHighestPrice(false);
            Attribute a = attService.postAttribute(attribute);
            product.getAttributes().add(a);
            product.getAttributes().get(0).setValues(new ArrayList());
        }
        product.getAttributes().get(0).getValues().add(new AttributeValue());
    }

    public void removeAttribute(AttributeValue att) {
        attribute.getValues().remove(att);
    }

    public void setAttributeSku(AttributeValue av) {
        av.setAttribute_sku(product.getAttributes().get(0).getSku());
    }
}
