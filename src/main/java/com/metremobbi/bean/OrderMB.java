/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.bean;

import com.metremobbi.model.Order;
import com.metremobbi.model.filter.OrderFilter;
import com.metremobbi.service.OrderService;
import com.metremobbi.util.OUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Renato
 */
@ManagedBean(eager = true)
@ViewScoped
public class OrderMB {

    OrderService service = new OrderService();

    @Getter
    @Setter
    private OrderFilter filtro = new OrderFilter();
    @Getter
    @Setter
    public List<Order> orders = new ArrayList();
    @Getter
    @Setter
    private Double totalRelatorio = 0.0;

    public OrderMB() {
    }

    public void pesquisar() {
        try {
            if (filtro.getDatas().size() > 1) {
                if (filtro.getDatas().get(0).equals(filtro.getDatas().get(1))) {
                    filtro.getDatas().set(1, OUtils.addSegundo(OUtils.addMinuto(OUtils.addHour(filtro.getDatas().get(1), 23), 59), 59));
                }
            }
            orders = service.pesquisar(filtro);
            totalRelatorio = 0.0;
            for (Order o : orders) {
                totalRelatorio = totalRelatorio.doubleValue() + o.getTotal();
            }
            System.out.println("VEIO: " + orders.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Date maxDate() {
        Date date = new Date();
        return date;
    }

    public String formataMoeda(Double bd) {
        if (bd == null) {
            return OUtils.formatarMoeda(0.0);
        } else {
            return OUtils.formatarMoeda(bd);
        }
    }

}
