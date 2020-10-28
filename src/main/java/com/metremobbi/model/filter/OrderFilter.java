/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.model.filter;

import com.metremobbi.util.OUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Renato
 */
public class OrderFilter {

    @Setter
    private List<Date> datas = new ArrayList();
    @Getter
    @Setter
    private String pedido;
    @Getter
    @Setter
    private String nomeCliente;
    @Getter
    @Setter
    private String phone;

    public List<Date> getDatas() {
        if (datas.size() == 0) {
            datas.add(OUtils.primeiroDiaDoAno());
            datas.add(new Date());
        }
        return datas;
    }

}
