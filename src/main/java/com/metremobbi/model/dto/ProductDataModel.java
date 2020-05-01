/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.model.dto;

import com.metremobbi.model.Product;
import com.metremobbi.model.Product;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Renato
 */
public class ProductDataModel extends
        ListDataModel<Product> implements SelectableDataModel<Product> {

    public ProductDataModel() {
    }

    public ProductDataModel(List<Product> data) {
        super(data);
    }

    @Override
    public Object getRowKey(Product t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Product getRowData(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 

}
