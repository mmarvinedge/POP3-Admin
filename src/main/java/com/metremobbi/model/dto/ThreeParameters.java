/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Renato
 */
@Getter
@Setter
public class ThreeParameters {

    private String _id;
    private String qnt;
    private String total;

    public ThreeParameters() {
    }

    public ThreeParameters(String _id, String qnt) {
        this._id = _id;
        this.qnt = qnt;
    }

    public ThreeParameters(String _id, String qnt, String total) {
        this._id = _id;
        this.qnt = qnt;
        this.total = total;
    }

    @Override
    public String toString() {
        return "TwoParameters{" + "_id=" + _id + ", qnt=" + qnt + '}';
    }

}
