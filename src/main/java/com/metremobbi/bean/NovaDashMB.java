/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.bean;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;

/**
 *
 * @author Renato
 */
@ManagedBean(eager = true)
@ViewScoped
public class NovaDashMB {

    @Getter
    @Setter
    private LineChartModel vendasDiarias = new LineChartModel();

    public NovaDashMB() {
        createChartVendasDiarias();
    }
    
    
    private void createChartVendasDiarias(){
        ChartData data = new ChartData();
         
        LineChartDataSet dataSet = new LineChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(65);
        values.add(59);
        values.add(80);
        values.add(81);
        values.add(56);
        values.add(55);
        values.add(40); 
        dataSet.setData(values); 
        dataSet.setFill(false);
        dataSet.setLabel("212222");
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setLineTension(0.1);
        data.addChartDataSet(dataSet);
         
        List<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        data.setLabels(labels);
         
        //Options
        LineChartOptions options = new LineChartOptions();        
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Vendas Diárias");
        options.setTitle(title);
         
        vendasDiarias.setOptions(options);
        vendasDiarias.setData(data);
    }

}
