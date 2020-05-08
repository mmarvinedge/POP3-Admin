/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.metremobbi.model.Order;
import com.metremobbi.model.Product;
import com.metremobbi.util.Constantes;
import com.metremobbi.util.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * @author Márvin Edge
 */
public class OrderService {
    
    private final String companyID = Utils.usuarioLogado().getCompanyId();

    OkHttpClient client = new OkHttpClient();

    private final OkHttpClient httpClient = new OkHttpClient();
    
    public List<Order> getOrdersDay() throws IOException {
        List<Order> saida = new ArrayList();
        Request request = new Request.Builder()
                .url(Constantes.URL + "/order/day")
                .header("company_id", companyID)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            // Get response body
            String json = response.body().string();
            System.out.println(json);

            saida = new Gson().fromJson(json, new TypeToken<List<Order>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            saida = new ArrayList();
        }
        return saida;
    }
    
    public List<Order> getOrdersWeek() throws IOException {
        List<Order> saida = new ArrayList();
        Request request = new Request.Builder()
                .url(Constantes.URL + "/order/week")
                .header("company_id", companyID)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            // Get response body
            String json = response.body().string();

            saida = new Gson().fromJson(json, new TypeToken<List<Order>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            saida = new ArrayList();
        }
        return saida;
    }
    
    public List<Order> getOrdersMonth() throws IOException {
        List<Order> saida = new ArrayList();
        Request request = new Request.Builder()
                .url(Constantes.URL + "/order/month")
                .header("company_id", companyID)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            // Get response body
            String json = response.body().string();
            saida = new Gson().fromJson(json, new TypeToken<List<Order>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            saida = new ArrayList();
        }
        return saida;
    }
    
}
