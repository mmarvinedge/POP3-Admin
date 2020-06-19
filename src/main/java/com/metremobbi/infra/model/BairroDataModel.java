/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.infra.model;

import com.metremobbi.model.dto.Bairro;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author snow
 */
public class BairroDataModel extends ListDataModel<Bairro> implements SelectableDataModel<Bairro> {

    public BairroDataModel() {
    }

    public BairroDataModel(List<Bairro> data) {
        super(data);
    }

    @Override
    public Bairro getRowData(String rowKey) {
        List<Bairro> cars = (List<Bairro>) getWrappedData();

        for (Bairro product : cars) {
            if (product.getBairro().equals(rowKey)) {
                return product;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(Bairro product) {
        return product.getBairro();
    }

    public List<Bairro> getBairros() {
        List<Bairro> bairros = (List<Bairro>) getWrappedData();
        return bairros;
    }

}
