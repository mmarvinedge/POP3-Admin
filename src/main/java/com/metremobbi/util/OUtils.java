/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author Renato
 */
public class OUtils {

    public static String formataData(Date data, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern, new Locale("pt", "BR"));
        if (data != null) {
            return df.format(data);
        } else {
            return "";
        }
    }
    
    public static String formataNinePhone(String tel){
          String fone = tel;
        //String fone = "553438233745";
        String whitou55 = fone.subSequence(2, fone.length()).toString();
        String ddd = whitou55.subSequence(0, 2).toString();
        String whitoudd = whitou55.substring(2, whitou55.length()).toString();
        
        Boolean isCelular = whitoudd.startsWith("9") || whitoudd.startsWith("8");
        if(isCelular){
            return "("+ddd+")9"+whitoudd.substring(0, 4)+"-"+whitoudd.substring(4, 8);
        }else{
            return "("+ddd+")"+whitoudd.substring(0, 4)+"-"+whitoudd.substring(4, 8);
            
        }
        
    }
    
    public static String formatarMoeda(Double val) {
        if (val == null) {
            return "R$ 0,00";
        }
        try {

            NumberFormat nF = NumberFormat.getCurrencyInstance();
            nF.setCurrency(Currency.getInstance(new Locale("pt", "BR")));
            return nF.format(val).replace("BRL", "R$ ");
        } catch (Exception e) {
            System.out.println("ERRO AO TENTAR FORMATAR: " + val);
            return "R$ " + val;
        }
    }
    
    public static Date getDataByTexto(String data, String formato) {
        try {
            Date date = null;
            DateFormat formatter = new SimpleDateFormat(formato);
            date = (java.util.Date) formatter.parse(data);
            return date;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static Date primeiroDiaDaSemana() {
        Calendar data = new GregorianCalendar();
        int ultimo_dia_mes = data.getActualMinimum(Calendar.DAY_OF_WEEK);
        data.set(Calendar.DAY_OF_WEEK, ultimo_dia_mes);
        return data.getTime();
    }
    
    public static Date addDia(Date data, int qtd) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.DAY_OF_MONTH, qtd);
        return cal.getTime();
    }
}
