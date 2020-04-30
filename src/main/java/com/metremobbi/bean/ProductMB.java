/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.bean;

import com.metremobbi.infra.model.ProductDataModel;
import static com.metremobbi.util.Utils.addDetailMessage;
import com.metremobbi.model.Product;
import com.metremobbi.model.Attribute;
import com.metremobbi.model.AttributeValue;
import com.metremobbi.model.Category;
import com.metremobbi.model.FlavorPizza;
import com.metremobbi.service.AttributeService;
import com.metremobbi.service.ProductService;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.binary.Base64;
import org.primefaces.PrimeFaces;
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
    List<Attribute> attributes;
    @Getter
    @Setter
    private String type;
    @Getter
    @Setter
    private Attribute attribute;
    @Getter
    @Setter
    private AttributeService attService;
    @Getter
    @Setter
    private FlavorPizza flavorPizza = new FlavorPizza();
    @Getter
    @Setter
    private ProductDataModel productModel;

    public ProductMB() {
        products = new ArrayList<>();
        product = new Product();
        product.setCategoryMain(new Category());
        service = new ProductService();
        attributes = new ArrayList<>();
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
        productModel = new ProductDataModel(products);
        categoryList = service.getCategoryList();
    }

    public void update(Product p) throws IOException {
        service.putProduct(p);
    }

    public void save() throws IOException {

        if (product.getId() == null) {
            try {
                service.postProduct(product);
                //products.add(product);
                addDetailMessage("Produto Salvo com sucesso!");
                System.out.println("INDEX:> " + products.indexOf(product));
                if (products.indexOf(product) > -1) {
                    products.set(products.indexOf(product), product);
                } else {
                    products.add(product);
                }
                get();
            } catch (Exception e) {
                e.printStackTrace();
                addDetailMessage("Não foi possível salvar", FacesMessage.SEVERITY_ERROR);
            }
        } else {
            service.putProduct(product);
            addDetailMessage("Produto atualizado com sucesso!");
            get();
        }
    }

    public void delete() throws IOException {
        service.deleteProduct(selectedProducts);
        products.removeAll(selectedProducts);
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
            if(event.getFile().getFileName().contains(".jpeg")) {
                product.setImageType("JPEG");
            } else if (event.getFile().getFileName().contains(".jpg")) {
                product.setImageType("JPG");
            } else if (event.getFile().getFileName().contains(".png")) {
                product.setImageType("PNG");
            } 
            product.setImageBase64(imageStr);
        } catch (Exception e) {
        }
    }

    public void debug() {
        System.out.println("value Category: " + product.getCategoryMain().getName());
    }

    public void addAttributeCorrect() throws IOException {
        if (product.getAttributes() == null) {
            product.setAttributes(new ArrayList());
        }
        Attribute atr = new Attribute();
        atr.setQuantity(1);
        product.getAttributes().add(atr);
    }

    public void addAttributeValue(Attribute at) throws IOException {
        if (at.getValues() == null) {
            at.setValues(new ArrayList());
        }
        AttributeValue atr = new AttributeValue();
        at.getValues().add(atr);
    }

    public void removeAtribute(Attribute atr) {
        product.getAttributes().remove(atr);
    }

    public void removeAtributeValue(Attribute at, AttributeValue atv) {
        at.getValues().remove(atv);
    }

    public void addAttribute() throws IOException {
        if (product.getAttributes() == null) {
            product.setAttributes(new ArrayList());
        }

//        if (type.equalsIgnoreCase("normal") && attribute.getValues().size() == 0) {
        if (type.equalsIgnoreCase("normal") && attribute.getValues() == null) {
            createAttributes();
        }
        AttributeValue av = new AttributeValue();
        av.setPrice(BigDecimal.ZERO);
        attribute.getValues().add(av);
    }

    private void createAttributes() {
        attribute.setValues(new ArrayList<>());
        attribute.setName("Adicionais");
        attribute.setDescription("Complementos adicionais");
        attribute.setQuantity(4);
        attribute.setQuantityType("max");
        attribute.setType("multiple selection");
        attribute.setHighestPrice(false);
    }

    private void addAttributesInProduct() throws IOException {
        attService = new AttributeService();
        if (attribute.getId() == null) {
            Attribute a = attService.postAttribute(attribute);
            a.getValues().forEach(v -> {
                v.setAttribute_sku(a.getSku());
            });
            product.getAttributes().add(a);
            product.getAttributes().get(0).setValues(a.getValues());
        } else {
            //editar o attribute
            Attribute a = attService.putAttribute(attribute);
            a.getValues().forEach(v -> {
                v.setAttribute_sku(a.getSku());
            });
            product.setAttributes(new ArrayList<>());
            product.getAttributes().add(a);
            product.getAttributes().get(0).setValues(a.getValues());

        }

    }

    public void removeAttribute(int index, AttributeValue att) throws IOException {
        attribute.getValues().remove(index);
        if (attribute.getId() != null) {
            attService.deleteAttribute(attribute);
        }
    }

    //se nao for na linha nao precisa desse
    public void setProductComplete() {
        product = selectedProducts.get(0);
        if (product != null && product.getAttributes() != null && product.getAttributes().size() > 0 && product.getAttributes().get(0) != null) {
            attribute = product.getAttributes().get(0);
        }
    }
    
    public void copyProduct() {
        product = new Product();
        setProductComplete();
        product.setId(null);
        product.setSku(null);
        product.setOrder(null);
        attribute.setId(null);
        attribute.setSku(null);
    }

    public void addFlavor() {
        if (product.getFlavorsPizza() == null) {
            product.setFlavorsPizza(new ArrayList());
        }
        product.getFlavorsPizza().add(flavorPizza);
        flavorPizza = new FlavorPizza();
    }

    public void removeFlavor(FlavorPizza fla) {
        product.getFlavorsPizza().remove(fla);
        System.out.println("SIZE: " + product.getFlavorsPizza());
    }

}
