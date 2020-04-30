/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.infra.model;

import com.metremobbi.model.Product;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author snow
 */
public class ProductDataModel extends ListDataModel<Product> implements  SelectableDataModel<Product>{
    
    public ProductDataModel() {
    }

    public ProductDataModel(List<Product> data) {
        super(data);
    }

    @Override
    public Product getRowData(String rowKey) {
        List<Product> cars = (List<Product>) getWrappedData();

        for(Product product : cars) {
            if(product.getId().equals(rowKey))
                return product;
        }

        return null;
    }

    @Override
    public Object getRowKey(Product product) {
        return product.getId();
    }
    
}
