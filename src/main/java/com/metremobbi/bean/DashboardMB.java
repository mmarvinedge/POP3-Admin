/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.bean;

import com.metremobbi.model.Item;
import com.metremobbi.model.Order;
import com.metremobbi.model.dto.RankingProduct;
import com.metremobbi.service.OrderService;
import com.metremobbi.util.OUtils;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Márvin Edge
 */
@ManagedBean(eager = true)
@ViewScoped
public class DashboardMB implements Serializable {

    @Getter
    @Setter
    private OrderService orderService;
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
    private String typeDashboard;
    @Getter
    @Setter
    private List<RankingProduct> ranking;
    
    public DashboardMB() {
        listOrder = new ArrayList();
        orderService = new OrderService();
        typeDashboard = "day";
        ranking = new ArrayList();
    }

    @PostConstruct
    public void init() {
        get();
    }

    public void get() {
        try {
            listOrder = orderService.getOrdersMonth();
            setTypeDashboard("day");
        } catch (IOException ex) {
            listOrder = new ArrayList();
            totalSold = 0.0;
            totalDelivery = 0.0;
            orders = 0;
            ex.printStackTrace();
        }
    }

    public void setTypeDashboard(String type) {
        typeDashboard = type;
        if(typeDashboard.equals("day")){
            loadDashboardDay(listOrder);
        } else if (typeDashboard.equals("week")) {
            loadDashboardWeek(listOrder);
        } else if (typeDashboard.equals("month")) {
            loadDashboardMonth(listOrder);
        }
    }

    public void loadDashboardDay(List<Order> orderss) {
        List<Item> itens = new ArrayList();
        List<Order> listDay = orderss.stream().filter(s -> s.getDtRegister().equals(OUtils.formataData(new Date(), "yyyy-MM-dd")))
                .collect(Collectors.toList());
        totalSold = listDay.stream().map(o -> o.getTotal()).mapToDouble(Double::doubleValue).sum();
        orders = listDay.size();
        totalDelivery = listDay.stream().map(o -> o.getDeliveryCost()).mapToDouble(Double::doubleValue).sum();
        for (Order o : listDay) {
            for (Item i : o.getProducts()) {
                itens.add(i);
            }
        }
        ranking = new ArrayList();
        Collection<List<Item>> saida = itens.stream().collect(Collectors.groupingBy(Item::getName)).values();
        for (List<Item> itemAgrupado : saida) {
            BigDecimal qnt = itemAgrupado.stream().map(p-> p.getQuantity()).reduce(BigDecimal.ZERO,BigDecimal::add);
            BigDecimal total = itemAgrupado.stream().map(p-> p.getTotal()).reduce(BigDecimal.ZERO,BigDecimal::add);
            RankingProduct rp = new RankingProduct(itemAgrupado.get(0).getName(), qnt, total);
            ranking.add(rp);
        }
    }

    public void loadDashboardWeek(List<Order> orderss) {
        List<Item> itens = new ArrayList();
        List<Order> listWeek = new ArrayList();
        for (Order o : orderss) {
            if (o.getDtRegister().equals(OUtils.formataData(OUtils.primeiroDiaDaSemana(), "yyyy-MM-dd"))) {
                listWeek.add(o);
            } else if (o.getDtRegister().equals(OUtils.formataData(OUtils.addDia(OUtils.primeiroDiaDaSemana(), 1), "yyyy-MM-dd"))) {
                listWeek.add(o);
            } else if (o.getDtRegister().equals(OUtils.formataData(OUtils.addDia(OUtils.primeiroDiaDaSemana(), 2), "yyyy-MM-dd"))) {
                listWeek.add(o);
            } else if (o.getDtRegister().equals(OUtils.formataData(OUtils.addDia(OUtils.primeiroDiaDaSemana(), 3), "yyyy-MM-dd"))) {
                listWeek.add(o);
            } else if (o.getDtRegister().equals(OUtils.formataData(OUtils.addDia(OUtils.primeiroDiaDaSemana(), 4), "yyyy-MM-dd"))) {
                listWeek.add(o);
            } else if (o.getDtRegister().equals(OUtils.formataData(OUtils.addDia(OUtils.primeiroDiaDaSemana(), 5), "yyyy-MM-dd"))) {
                listWeek.add(o);
            } else if (o.getDtRegister().equals(OUtils.formataData(OUtils.addDia(OUtils.primeiroDiaDaSemana(), 6), "yyyy-MM-dd"))) {
                listWeek.add(o);
            }
        }
        totalSold = listWeek.stream().map(o -> o.getTotal()).mapToDouble(Double::doubleValue).sum();
        orders = listWeek.size();
        totalDelivery = listWeek.stream().map(o -> o.getDeliveryCost()).mapToDouble(Double::doubleValue).sum();
        for (Order o : listWeek) {
            for (Item i : o.getProducts()) {
                itens.add(i);
            }
        }
        ranking = new ArrayList();
        Collection<List<Item>> saida = itens.stream().collect(Collectors.groupingBy(Item::getName)).values();
        for (List<Item> itemAgrupado : saida) {
            BigDecimal qnt = itemAgrupado.stream().map(p-> p.getQuantity()).reduce(BigDecimal.ZERO,BigDecimal::add);
            BigDecimal total = itemAgrupado.stream().map(p-> p.getTotal()).reduce(BigDecimal.ZERO,BigDecimal::add);
            RankingProduct rp = new RankingProduct(itemAgrupado.get(0).getName(), qnt, total);
            ranking.add(rp);
        }
    }

    public void loadDashboardMonth(List<Order> orderss) {
        List<Item> itens = new ArrayList();
        totalSold = orderss.stream().map(o -> o.getTotal()).mapToDouble(Double::doubleValue).sum();
        orders = orderss.size();
        totalDelivery = orderss.stream().map(o -> o.getDeliveryCost()).mapToDouble(Double::doubleValue).sum();
        for (Order o : orderss) {
            for (Item i : o.getProducts()) {
                itens.add(i);
            }
        }
        ranking = new ArrayList();
        Collection<List<Item>> saida = itens.stream().collect(Collectors.groupingBy(Item::getName)).values();
        for (List<Item> itemAgrupado : saida) {
            BigDecimal qnt = itemAgrupado.stream().map(p-> p.getQuantity()).reduce(BigDecimal.ZERO,BigDecimal::add);
            BigDecimal total = itemAgrupado.stream().map(p-> p.getTotal()).reduce(BigDecimal.ZERO,BigDecimal::add);
            RankingProduct rp = new RankingProduct(itemAgrupado.get(0).getName(), qnt, total);
            ranking.add(rp);
        }
    }

}
