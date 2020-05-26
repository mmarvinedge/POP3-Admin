/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.util;

import java.math.BigInteger;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Marvin
 */
public class DateUtil {
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalTime localDate) {
        return new Date(2000, 1, 1, localDate.getHour(), localDate.getMinute(), localDate.getSecond());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(LocalTime localTime) {
        Instant instant = localTime.atDate(LocalDate.now())
                .atZone(ZoneId.systemDefault()).toInstant();
        return toDate2(instant);
    }

    public static Date toDate2(Instant instant) {
        BigInteger milis = BigInteger.valueOf(instant.getEpochSecond()).multiply(
                BigInteger.valueOf(1000));
        milis = milis.add(BigInteger.valueOf(instant.getNano()).divide(
                BigInteger.valueOf(1_000_000)));
        return new Date(milis.longValue());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Integer minutesBetweenDates(Date dataInicial, Date dataFinal) {
        long diff = dataFinal.getTime() - dataInicial.getTime();
//        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
//        long diffHours = diff / (60 * 60 * 1000);
        return Integer.parseInt(diffMinutes + "");
    }

    public static long diferencaEntreDatas(String tipo, Date dataInicio, Date dataFim) {

        Date dataDe = dataInicio;
        Date dataAte = dataFim;
        long diferencaSegundos = (dataAte.getTime() - dataDe.getTime()) / (1000);
        long diferencaMinutos = (dataAte.getTime() - dataDe.getTime()) / (1000 * 60);
        long diferencaHoras = (dataAte.getTime() - dataDe.getTime()) / (1000 * 60 * 60);
        long diferencaDias = (dataAte.getTime() - dataDe.getTime()) / (1000 * 60 * 60 * 24);
        long diferencaMeses = (dataAte.getTime() - dataDe.getTime()) / (1000 * 60 * 60 * 24) / 30;
        long diferencaAnos = ((dataAte.getTime() - dataDe.getTime()) / (1000 * 60 * 60 * 24) / 30) / 12;

        return diferencaDias;
    }

    public static LocalTime asLocalTime(Date date) {
        DateTimeFormatter germanFormatter
                = DateTimeFormatter
                        .ofLocalizedTime(FormatStyle.MEDIUM)
                        .withLocale(Locale.US);
        ZoneId zone2 = ZoneId.of("Brazil/East");
        LocalTime leetTime = LocalTime.parse(Instant.ofEpochMilli(date.getTime()).atZone(zone2).toLocalTime().format(germanFormatter), germanFormatter);
        return leetTime;
    }

    public static Boolean maior(Date ini, Date fim) {
        return ini.after(fim) || ini.equals(fim);
    }

    public static Boolean menor(Date ini, Date fim) {
        return ini.before(fim);
    }

    public static Boolean maior(LocalTime ini, LocalTime fim) {
        return ini.isAfter(fim) || ini.equals(fim);
    }

    public static Boolean menor(LocalTime ini, LocalTime fim) {
        return ini.isBefore(fim);
    }

    public static Date addMes(Date data, int qtd) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.MONTH, qtd);
        return cal.getTime();
    }

    public static Date addDia(Date data, int qtd) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.DAY_OF_MONTH, qtd);
        return cal.getTime();
    }

    public static Date getDate(Date date, Time time) {
        Calendar dCal = Calendar.getInstance();
        dCal.setTime(date);
        Calendar tCal = Calendar.getInstance();
        tCal.setTime(time);
        dCal.set(Calendar.HOUR_OF_DAY, tCal.get(Calendar.HOUR_OF_DAY));
        dCal.set(Calendar.MINUTE, tCal.get(Calendar.MINUTE));
        dCal.set(Calendar.SECOND, tCal.get(Calendar.SECOND));
        dCal.set(Calendar.MILLISECOND, tCal.get(Calendar.MILLISECOND));
        date = dCal.getTime();
        return date;
    }

    public static Date addHour(Date data, int qtd) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.HOUR_OF_DAY, qtd);
        return cal.getTime();
    }

    public static Date subDia(Date data, int qtd) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.DAY_OF_MONTH, -qtd);
        return cal.getTime();
    }

    public static boolean isBetween(LocalTime candidate, LocalTime start, LocalTime end) {
        return !candidate.isBefore(start) && !candidate.isAfter(end);
    }

    public static Date addHora(Date data, int qtd) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.HOUR_OF_DAY, qtd);
        return cal.getTime();
    }

    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static String DiaDaSemana() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int day = cal.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case 1:
                return "Domingo";
            case 2:
                return "Segunda";
            case 3:
                return "Terça";
            case 4:
                return "Quarta";
            case 5:
                return "Quinta";
            case 6:
                return "Sexta";
            case 7:
                return "Sabado";
            default:
                return "";
        }
    }

    public static Integer DiaDaSemanaInt() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int day = cal.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 5;
            case 7:
                return 6;
            default:
                return 0;
        }
    }

    public static Boolean verificarDataLimite(Date dateCor) {
        Time limite = new Time(6, 0, 0);
        Time time = new Time(dateCor.getTime());
        return time.before(limite);
    }

    public static String DiaDaSemana(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case 1:
                return "Domingo";
            case 2:
                return "Segunda";
            case 3:
                return "Terça";
            case 4:
                return "Quarta";
            case 5:
                return "Quinta";
            case 6:
                return "Sexta";
            case 7:
                return "Sábado";
            default:
                return "";
        }
    }

    public static String toDateYYYYMMDD(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    public static String toDateDDMMYY(Date date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        return df.format(date);
    }

    public static String toDateYYYYMMDDHHMMSS(Date date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        if (date == null) {
            return "";
        } else {
            return df.format(date);
        }
    }

    public static String toDateYYYYMMDDHHMMSSSQL(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        if (date == null) {
            return "";
        } else {
            return df.format(date);
        }
    }

    public static String toDateYYYYMMDDSQL(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        if (date == null) {
            return "";
        } else {
            return df.format(date);
        }
    }

    public static String toDateYYYYMMDDHHMMSSponto(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        if (date == null) {
            return "";
        } else {
            return df.format(date);
        }
    }

    public static String HHMMSS(Date date) {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(date);
    }

    public static Date formatarDataErradaJSON(String data) {
        // FORMATO RECEBIDO DEVE SER ESSE : abr 10, 2019
        String mes = formatarMes(data.substring(0, 3));
        String dia = data.substring(3, 6);
        String ano = data.substring(8, 12);
        return converterDateToTime("00:00:00" + dia + "/" + mes + "/" + ano);

    }

    public static String formatarMes(String mes) {
        switch (mes) {
            case "jan":
                return "00";
            case "fev":
                return "01";
            case "mar":
                return "02";
            case "abr":
                return "03";
            case "mai":
                return "04";
            case "jun":
                return "05";
            case "jul":
                return "06";
            case "ago":
                return "07";
            case "set":
                return "08";
            case "out":
                return "09";
            case "nov":
                return "10";
            default:
                return "11";
        }
    }

    public static Date converterDateToTime(String s) {
        //"09:32:00 19/09/2016"
        Calendar cal = Calendar.getInstance();
        String hora = s.substring(0, 2);
        String minuto = s.substring(3, 5);
        String segundo = s.substring(6, 8);

        String dia = s.substring(9, 11);
        String mes = s.substring(12, 14);
        String ano = s.substring(15, 19);

        cal.set(Calendar.DATE, Integer.parseInt(dia.trim()));
        cal.set(Calendar.MONTH, Integer.parseInt(mes.trim()));
        cal.set(Calendar.YEAR, Integer.parseInt(ano.trim()));

        cal.set(Calendar.HOUR, Integer.parseInt(hora));
        cal.set(Calendar.MINUTE, Integer.parseInt(minuto));
        cal.set(Calendar.SECOND, Integer.parseInt(segundo));
        return cal.getTime();
    }

    public static Date adicionarHoraEmData(Date date, Date time) {
        Calendar t = Calendar.getInstance();
        t.setTime(time);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, t.get(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, t.get(Calendar.MINUTE));
        c.set(Calendar.SECOND, t.get(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, t.get(Calendar.MILLISECOND));
        return c.getTime();
    }
}
