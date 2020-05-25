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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
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
    private Long orders, ordersCancel, ordersWait;
    @Getter
    @Setter
    private List<Order> listOrder;
    @Getter
    @Setter
    private String typeDashboard, labelWeek;
    @Getter
    @Setter
    private List<RankingProduct> ranking;
    @Getter
    @Setter
    private Double monday, tuesday, wednesday, thursday, friday, saturday, sunday = 0.0;
    @Getter
    @Setter
    private Double january, fabruary, march, april, may, june, july, august, september, october, november, december = 0.0;
    @Getter
    @Setter
    private StringBuilder valueWeek, valueMonth = new StringBuilder();
    @Getter
    @Setter
    private String begginWeek, endWeek;

    public DashboardMB() {
        listOrder = new ArrayList();
        orderService = new OrderService();
        typeDashboard = "day";
        ranking = new ArrayList();
        begginWeek = OUtils.formataData(OUtils.primeiroDiaDaSemana(), "dd/MM/yyyy");
        endWeek = OUtils.formataData(OUtils.addDia(OUtils.primeiroDiaDaSemana(), 6), "dd/MM/yyyy");
        labelWeek = "[\"Domingo\",\"Segunda\",\"Terça\",\"Quarta\",\"Quinta\",\"Sexta\",\"Sabado\"]";
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
            orders = 0L;
            ex.printStackTrace();
        }
    }

    public void setTypeDashboard(String type) {
        typeDashboard = type;
        switch (typeDashboard) {
            case "day":
                loadDashboardDay(listOrder);
                break;
            case "week":
                loadDashboardWeek(listOrder);
                break;
            case "month":
                loadDashboardMonth(listOrder);
                break;
            default:
                break;
        }
    }

    public void loadDashboardDay(List<Order> orderss) {
        try {
            List<Item> itens = new ArrayList();
            List<Order> listDay = orderss.stream().filter(s -> s.getDtRegister().equals(OUtils.formataData(new Date(), "yyyy-MM-dd")))
                    .collect(Collectors.toList());
            totalSold = listDay.stream().filter(p -> p.getStatus().equals("Finalizado")).map(o -> o.getTotal()).mapToDouble(Double::doubleValue).sum();
            orders = listDay.stream().filter(p -> p.getStatus().equals("Finalizado")).count();
            ordersWait = listDay.stream().filter(p -> !p.getStatus().equals("Finalizado") && !p.getStatus().equals("Cancelado")).count();
            ordersCancel = listDay.stream().filter(p -> p.getStatus().equals("Cancelado")).count();
            totalDelivery = listDay.stream().filter(p -> p.getStatus().equals("Finalizado")).map(o -> o.getDeliveryCost()).mapToDouble(Double::doubleValue).sum();
            for (Order o : listDay) {
                for (Item i : o.getProducts()) {
                    itens.add(i);
                }
            }
            ranking = new ArrayList();
            Collection<List<Item>> saida = itens.stream().collect(Collectors.groupingBy(Item::getName)).values();
            for (List<Item> itemAgrupado : saida) {
                BigDecimal qnt = itemAgrupado.stream().map(p -> p.getQuantity()).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal total = itemAgrupado.stream().map(p -> p.getTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
                RankingProduct rp = new RankingProduct(itemAgrupado.get(0).getName(), qnt, total);
                ranking.add(rp);
            }
            ranking.sort(Comparator.comparingDouble(RankingProduct::getQnt).reversed());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadDashboardWeek(List<Order> orderss) {
        List<Item> itens = new ArrayList();
        List<Order> listWeek = new ArrayList();
        valueWeek = new StringBuilder();
        clean();
        System.out.println(OUtils.primeiroDiaDaSemana());
        for (Order o : orderss) {
            if (o.getDtRegister().equals(OUtils.formataData(OUtils.primeiroDiaDaSemana(), "yyyy-MM-dd"))) {
                listWeek.add(o);
                if (o.getStatus().equals("Finalizado") && o.getTotal() != null) {
                    sunday = sunday + o.getTotal();
                }
            } else if (o.getDtRegister().toString().contains(OUtils.formataData(OUtils.addDia(OUtils.primeiroDiaDaSemana(), 1), "yyyy-MM-dd"))) {
                listWeek.add(o);
                if (o.getStatus().equals("Finalizado") && o.getTotal() != null) {
                    monday = monday + o.getTotal();
                }
            } else if (o.getDtRegister().toString().contains(OUtils.formataData(OUtils.addDia(OUtils.primeiroDiaDaSemana(), 2), "yyyy-MM-dd"))) {
                listWeek.add(o);
                if (o.getStatus().equals("Finalizado") && o.getTotal() != null) {
                    tuesday = tuesday + o.getTotal();
                }
            } else if (o.getDtRegister().toString().contains(OUtils.formataData(OUtils.addDia(OUtils.primeiroDiaDaSemana(), 3), "yyyy-MM-dd"))) {
                listWeek.add(o);
                if (o.getStatus().equals("Finalizado") && o.getTotal() != null) {
                    wednesday = wednesday + o.getTotal();
                }
            } else if (o.getDtRegister().toString().contains(OUtils.formataData(OUtils.addDia(OUtils.primeiroDiaDaSemana(), 4), "yyyy-MM-dd"))) {
                listWeek.add(o);
                if (o.getStatus().equals("Finalizado") && o.getTotal() != null) {
                    thursday = thursday + o.getTotal();
                }
            } else if (o.getDtRegister().toString().contains(OUtils.formataData(OUtils.addDia(OUtils.primeiroDiaDaSemana(), 5), "yyyy-MM-dd"))) {
                listWeek.add(o);
                if (o.getStatus().equals("Finalizado") && o.getTotal() != null) {
                    friday = friday + o.getTotal();
                }
            } else if (o.getDtRegister().toString().contains(OUtils.formataData(OUtils.addDia(OUtils.primeiroDiaDaSemana(), 6), "yyyy-MM-dd"))) {
                listWeek.add(o);
                if (o.getStatus().equals("Finalizado") && o.getTotal() != null) {
                    saturday = saturday + o.getTotal();
                }
            }
        }
        valueWeek.append("[").append(sunday).append(",").append(monday).append(",").append(tuesday).append(",")
                .append(wednesday).append(",").append(thursday).append(",").append(friday).append(",").append(saturday)
                .append("]");
        totalSold = listWeek.stream().filter(p -> p.getStatus().equals("Finalizado")).map(o -> o.getTotal()).mapToDouble(Double::doubleValue).sum();
        orders = listWeek.stream().filter(p -> p.getStatus().equals("Finalizado")).count();
        totalDelivery = listWeek.stream().filter(p -> p.getStatus().equals("Finalizado")).map(o -> o.getDeliveryCost()).mapToDouble(Double::doubleValue).sum();
        for (Order o : listWeek) {
            for (Item i : o.getProducts()) {
                itens.add(i);
            }
        }
        ranking = new ArrayList();
        Collection<List<Item>> saida = itens.stream().collect(Collectors.groupingBy(Item::getName)).values();
        for (List<Item> itemAgrupado : saida) {
            BigDecimal qnt = itemAgrupado.stream().map(p -> p.getQuantity()).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal total = itemAgrupado.stream().map(p -> p.getTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
            RankingProduct rp = new RankingProduct(itemAgrupado.get(0).getName(), qnt, total);
            ranking.add(rp);
        }
        ranking.sort(Comparator.comparingDouble(RankingProduct::getQnt).reversed());
    }

    public void loadDashboardMonth(List<Order> orderss) {
        List<Item> itens = new ArrayList();
        totalSold = orderss.stream().map(o -> o.getTotal()).mapToDouble(Double::doubleValue).sum();
        orders = orderss.stream().filter(p -> p.getStatus().equals("Finalizado")).count();
        totalDelivery = orderss.stream().map(o -> o.getDeliveryCost()).mapToDouble(Double::doubleValue).sum();
        valueMonth = new StringBuilder();
        clean();
        for (Order o : orderss) {
            for (Item i : o.getProducts()) {
                itens.add(i);
            }
            if (OUtils.DiaDaSemana(OUtils.getDataByTexto(o.getDtRegister().toString(), "yyyy-MM-dd")).equals("Domingo")
                    && o.getStatus().equalsIgnoreCase("Finalizado")) {
                sunday = sunday + o.getTotal();
            } else if (OUtils.DiaDaSemana(OUtils.getDataByTexto(o.getDtRegister().toString(), "yyyy-MM-dd")).equals("Segunda")
                    && o.getStatus().equalsIgnoreCase("Finalizado")) {
                monday = monday + o.getTotal();
            } else if (OUtils.DiaDaSemana(OUtils.getDataByTexto(o.getDtRegister().toString(), "yyyy-MM-dd")).equals("Terça")
                    && o.getStatus().equalsIgnoreCase("Finalizado")) {
                tuesday = tuesday + o.getTotal();
            } else if (OUtils.DiaDaSemana(OUtils.getDataByTexto(o.getDtRegister().toString(), "yyyy-MM-dd")).equals("Quarta")
                    && o.getStatus().equalsIgnoreCase("Finalizado")) {
                wednesday = wednesday + o.getTotal();
            } else if (OUtils.DiaDaSemana(OUtils.getDataByTexto(o.getDtRegister().toString(), "yyyy-MM-dd")).equals("Quinta")
                    && o.getStatus().equalsIgnoreCase("Finalizado")) {
                thursday = thursday + o.getTotal();
            } else if (OUtils.DiaDaSemana(OUtils.getDataByTexto(o.getDtRegister().toString(), "yyyy-MM-dd")).equals("Sexta")
                    && o.getStatus().equalsIgnoreCase("Finalizado")) {
                friday = friday + o.getTotal();
            } else if (OUtils.DiaDaSemana(OUtils.getDataByTexto(o.getDtRegister().toString(), "yyyy-MM-dd")).equals("Sabado")
                    && o.getStatus().equalsIgnoreCase("Finalizado")) {
                saturday = saturday + o.getTotal();
            }
        }

        valueMonth.append("[").append(sunday).append(",").append(monday).append(",").append(tuesday).append(",")
                .append(wednesday).append(",").append(thursday).append(",").append(friday).append(",").append(saturday)
                .append("]");

        ranking = new ArrayList();
        Collection<List<Item>> saida = itens.stream().collect(Collectors.groupingBy(Item::getName)).values();
        for (List<Item> itemAgrupado : saida) {
            BigDecimal qnt = itemAgrupado.stream().map(p -> p.getQuantity()).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal total = itemAgrupado.stream().map(p -> p.getTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
            RankingProduct rp = new RankingProduct(itemAgrupado.get(0).getName(), qnt, total);
            ranking.add(rp);
        }
        ranking.sort(Comparator.comparingDouble(RankingProduct::getQnt).reversed());
    }

    public void clean() {
        monday = 0.0;
        tuesday = 0.0;
        wednesday = 0.0;
        thursday = 0.0;
        friday = 0.0;
        saturday = 0.0;
        sunday = 0.0;
    }

}
