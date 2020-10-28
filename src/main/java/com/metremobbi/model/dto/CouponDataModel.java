/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.model.dto;

import com.metremobbi.model.CouponCode;
import com.metremobbi.model.Product;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Renato
 */
public class CouponDataModel extends
        ListDataModel<CouponCode> implements SelectableDataModel<CouponCode> {

    public CouponDataModel() {
    }

    public CouponDataModel(List<CouponCode> data) {
        super(data);
    }

    @Override
    public Object getRowKey(CouponCode coupon) {
        return coupon.getId();
    }

    @Override
    public CouponCode getRowData(String rowKey) {
        List<CouponCode> cars = (List<CouponCode>) getWrappedData();

        for (CouponCode coupon : cars) {
            if (coupon.getId().equals(rowKey)) {
                return coupon;
            }
        }

        return null;
    }

}
