/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.bean;

import com.metremobbi.model.Address;
import com.metremobbi.model.ClientInfo;
import com.metremobbi.model.Item;
import com.metremobbi.model.Order;
import com.metremobbi.model.Product;
import com.metremobbi.model.dto.ThreeParameters;
import com.metremobbi.model.filter.OrderFilter;
import com.metremobbi.service.OrderService;
import com.metremobbi.util.OUtils;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;

/**
 *
 * @author Renato
 */
@ManagedBean(eager = true)
@ViewScoped
public class NovaDashMB {

    private OrderService orderService = new OrderService();

    @Getter
    @Setter
    private LineChartModel vendasDiarias = new LineChartModel();
    @Getter
    @Setter
    private PieChartModel pieModel = new PieChartModel();
    @Getter
    @Setter
    private PieChartModel pieModelTipoEntrega = new PieChartModel();
    @Getter
    @Setter
    private String dtIni, dtFim;
    private List<ThreeParameters> listaCardsSituacao = new ArrayList();
    @Getter
    @Setter
    private List<ThreeParameters> listProdutos = new ArrayList();

    private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    private List<Product> productsFotos = new ArrayList();

    @Getter
    @Setter
    private List<Order> orders = new ArrayList();
    @Getter
    @Setter
    List<ThreeParameters> saidaTopTelefones = new ArrayList();
    @Getter
    @Setter
    List<ThreeParameters> saidaTopBairros = new ArrayList();
    @Getter
    @Setter
    private List<Order> orderByPhone = new ArrayList();

    public NovaDashMB() {

    }

    public void pesquisar() {
        try {
            System.out.println("PEsQUISOU");
            System.out.println("INI1; " + dtIni);
            System.out.println("FIm1 ; " + dtFim);
            Date ini = df.parse(dtIni);
            Date fim = df.parse(dtFim);
            listaCardsSituacao = orderService.getOrderStatus(ini, fim);
            System.out.println("INI; " + ini);
            System.out.println("FIm; " + fim);

            orders = orderService.pesquisar(ini,fim);
            System.out.println("ORDER. " + orders.size());
            productsFotos = orderService.listarFotosDeProdutos();
            createChartVendasDiarias();
            createChartTipoPedidos();
            criarCardsProdutos();
            createCardTopClientes();
            createCardTopBairros();
            createChartTipoEntrega();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createCardTopClientes() {
        saidaTopTelefones = new ArrayList();
        List<ClientInfo> infoClientes = orders.stream().filter(p -> p.getStatus().equals("Finalizado")).map(m -> m.getClientInfo()).collect(Collectors.toList());

        Map<String, Long> counted = infoClientes.stream().map(c -> c.getPhone())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for (Map.Entry<String, Long> entry : counted.entrySet()) {
            saidaTopTelefones.add(new ThreeParameters(entry.getKey(), entry.getValue().toString()));
        }
        saidaTopTelefones.sort(new Comparator<ThreeParameters>() {
            @Override
            public int compare(ThreeParameters o1, ThreeParameters o2) {
                return o2.getQnt().compareTo(o1.getQnt());
            }
        });

        if (saidaTopTelefones.size() > 5) {
            saidaTopTelefones = saidaTopTelefones.subList(0, 5);
        }
    }

    private void createCardTopBairros() {
        saidaTopBairros = new ArrayList();
        List<Address> infoClientes = orders.stream().filter(c -> c.getAddress() != null && c.getAddress().getAuto() != null
                && c.getStatus().equals("Finalizado")).map(m -> m.getAddress()).collect(Collectors.toList());

        Map<String, Long> counted = infoClientes.stream().map(c -> c.getAuto())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for (Map.Entry<String, Long> entry : counted.entrySet()) {
            saidaTopBairros.add(new ThreeParameters(entry.getKey(), entry.getValue().toString()));
        }
        saidaTopBairros.sort(new Comparator<ThreeParameters>() {
            @Override
            public int compare(ThreeParameters o1, ThreeParameters o2) {
                return o2.getQnt().compareTo(o1.getQnt());
            }
        });

        if (saidaTopBairros.size() > 5) {
            saidaTopBairros = saidaTopBairros.subList(0, 5);
        }
    }

    private void createChartVendasDiarias() {

        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();

        orders.sort((Order o1, Order o2) -> OUtils.getMonth(o1.getDtRegister()).compareTo(OUtils.getMonth(o1.getDtRegister())));
        List<Order> orderByMonth = orders.stream().filter(c -> c.getStatus().equalsIgnoreCase("Finalizado")).collect(Collectors.toList());
        Map<String, Long> result = orderByMonth.stream().map(c -> OUtils.getMonth(c.getDtRegister())).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<Number> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<String> months = new ArrayList();
        for (Map.Entry<String, Long> entry : result.entrySet()) {
            months.add(entry.getKey());
        }
        months.sort((String o1, String o2) -> o1.compareTo(o2));
        for (String month : months) {
            String mes = month;
            BigDecimal total = orderByMonth.stream().filter(c -> OUtils.getMonth(c.getDtRegister()).equals(mes)).map(cc -> new BigDecimal(cc.getTotal())).reduce(BigDecimal.ZERO, BigDecimal::add);
            values.add(total);
            labels.add(mes);
        }

        dataSet.setData(values);
        dataSet.setFill(false);
        dataSet.setLabel("Meses");
        dataSet.setBorderColor("#95c70d");
        dataSet.setLineTension(0.1);
        data.addChartDataSet(dataSet);

        data.setLabels(labels);

        //Options
        LineChartOptions options = new LineChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Vendas Mensais");
        options.setTitle(title);

        vendasDiarias.setOptions(options);
        vendasDiarias.setData(data);
    }

    private void createChartTipoPedidos() {
        pieModel = new PieChartModel();
        ChartData data = new ChartData();

        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
        try {
            long cancelados = orders.stream().filter(p -> p.getStatus().equals("Cancelado")).count();
            values.add(new BigDecimal(cancelados));
        } catch (Exception e) {
            values.add(0);
        }
        try {
            long finalizados = orders.stream().filter(p -> p.getStatus().equals("Finalizado")).count();
            values.add(new BigDecimal(finalizados));
        } catch (Exception e) {
            values.add(0);
        }
        dataSet.setData(values);
        List<String> bgColors = new ArrayList<>();
        bgColors.add("#ff413c");
        bgColors.add("#95c70d");

        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Cancelados");
        labels.add("Recebidos");
        data.setLabels(labels);

        pieModel.setData(data);
    }

    private void createChartTipoEntrega() {
        pieModelTipoEntrega = new PieChartModel();
        ChartData data = new ChartData();

        PieChartDataSet dataSet = new PieChartDataSet();
        List<Number> values = new ArrayList<>();
        try {
            long retirada = orders.stream().filter(p -> p.getStatus().equals("Finalizado") && !p.getDelivery()).count();
            values.add(new BigDecimal(retirada));
        } catch (Exception e) {
            values.add(0);
        }
        try {
            long entrega = orders.stream().filter(p -> p.getStatus().equals("Finalizado") && p.getDelivery()).count();
            values.add(new BigDecimal(entrega));
        } catch (Exception e) {
            values.add(0);
        }
        dataSet.setData(values);
        List<String> bgColors = new ArrayList<>();
        bgColors.add("#ff413c");
        bgColors.add("#95c70d");

        dataSet.setBackgroundColor(bgColors);

        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Retirada");
        labels.add("Entrega");
        data.setLabels(labels);

        pieModelTipoEntrega.setData(data);
    }

    private void criarCardsProdutos() {
        listProdutos = new ArrayList();
        List<Item> itens = new ArrayList();
        for (Order order : orders.stream().filter(c -> c.getStatus().equals("Finalizado")).collect(Collectors.toList())) {
            itens.addAll(order.getProducts());
        }
        Collection<List<Item>> itensPorProduto = itens.stream().collect(Collectors.groupingBy(Item::getName)).values();

        for (List<Item> list : itensPorProduto) {
            ThreeParameters tr = new ThreeParameters();
            tr.set_id(list.get(0).getName());
            tr.setQnt(list.stream().map(c -> c.getQuantity()).reduce(BigDecimal.ZERO, BigDecimal::add).toString());
            tr.setTotal(OUtils.formatarMoeda(list.stream().map(c -> c.getTotal()).reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue()));
            listProdutos.add(tr);
        }
        listProdutos.sort((ThreeParameters o1, ThreeParameters o2) -> new BigDecimal(o2.getQnt()).compareTo(new BigDecimal(o1.getQnt())));

    }

    public String retornaFoto(String nomeProduto) {
        Optional<Product> o = productsFotos.stream().filter(p -> p.getName().equalsIgnoreCase(nomeProduto)).findAny();
        if (o.isPresent()) {
            return o.get().getImageBase64();
        } else {
            return "https://cdn.cwsplatform.com/assets/no-photo-available.png";
        }
    }

    public void carregarOrderByPhone(String phone) {
        orderByPhone = orders.stream().filter(p -> p.getClientInfo().getPhone().equalsIgnoreCase(phone)).collect(Collectors.toList());
    }

}
