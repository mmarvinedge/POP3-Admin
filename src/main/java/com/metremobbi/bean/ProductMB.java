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
import com.metremobbi.model.Order;
import com.metremobbi.service.AttributeService;
import com.metremobbi.service.OrderService;
import com.metremobbi.service.ProductService;
import com.metremobbi.util.ImageFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
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
    private OrderService orderService;
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
    @Getter
    @Setter
    private Double totalSold, totalDelivery;
    @Getter
    @Setter
    private Integer orders;
    @Getter
    @Setter
    private List<Order> listOrder;
    @Getter
    @Setter
    private Category category;
    @Getter
    @Setter
    private Boolean situation;

    public ProductMB() {
        products = new ArrayList<>();
        product = new Product();
        product.setCategoryMain(new Category());
        service = new ProductService();
        attributes = new ArrayList<>();
        categoryList = new ArrayList<>();
        type = "normal";
        attribute = new Attribute();
        listOrder = new ArrayList();
        orderService = new OrderService();
        category = new Category();
        situation = true;
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
        selectedProducts = new ArrayList();
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
        File tempFile = null;
        try {
            tempFile = File.createTempFile("foto2", "png");
            InputStream is = event.getFile().getInputstream();
            FileOutputStream out = new FileOutputStream(tempFile);
            IOUtils.copy(is, out);

            String imageBase64 = ImageFile.encoder(tempFile.getAbsolutePath());
            System.out.println(imageBase64.length());
            product.setImageBase64(imageBase64);
        } catch (IOException e) {
            System.out.println("Erro ao converter a imagem em base64");
            e.printStackTrace();
        } finally {
            tempFile.deleteOnExit();
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
        product.setImageBase64(null);
        product.setImageType(null);
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

    public void updateCategorys() {
        try {
            service.updatecategorys();
            addDetailMessage("Categorias Atualizadas!", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            addDetailMessage("Não foi possível Atualizar", FacesMessage.SEVERITY_ERROR);
        }

    }

    public void stopSelling() {
        try {
            if (category.getName() == null) {
                addDetailMessage("É necessário selecionar uma categoria!", FacesMessage.SEVERITY_ERROR);
            } else {
                String set = "";
                if (situation) {
                    set = "true";
                } else {
                    set = "false";
                }
                service.disableProducts(category, set);
                addDetailMessage("Produtos atualizados com sucesso", FacesMessage.SEVERITY_INFO);
                category = new Category();
            }
        } catch (Exception e) {
            addDetailMessage("Não foi possível parar a venda dessa categoria", FacesMessage.SEVERITY_ERROR);
        }
    }

}
