/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.metremobbi.infra.security.LogonMB;
import com.metremobbi.model.Category;
import com.metremobbi.model.Product;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author PICHAU
 */
public class ProductService {

    @Getter
    private LogonMB logonMB;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    public static final String URL = "http://localhost:4000";

    private final OkHttpClient httpClient = new OkHttpClient();

    public List<Product> getProducts() throws IOException {
        List<Product> saida = new ArrayList();
        Request request = new Request.Builder()
                .url(URL + "/product/")
                .header("company_id", logonMB.getCompanyIdSession().getCompanyId())
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            // Get response body
            String json = response.body().string();
            System.out.println(json);

            saida = new Gson().fromJson(json, new TypeToken<List<Product>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            saida = new ArrayList();
        }
        return saida;
    }

    public void postProduct(Product product) throws IOException {
        RequestBody body = RequestBody.create(new Gson().toJson(product), JSON); // new
        // RequestBody body = RequestBody.create(JSON, json); // old
        Request request = new Request.Builder()
                .url(URL + "/product/save")
                .header("company_id", logonMB.getCompanyIdSession().getCompanyId())
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }

    public void deleteProduct(List<Product> products) throws IOException {
        for (Product p : products) {
            RequestBody body = RequestBody.create(new Gson().toJson(p), JSON); // new
            Request request = new Request.Builder()
                    .url(URL + "/product/")
                    .delete(body)
                    .build();
            System.out.println("vou deletar o produto "+ p.getName());
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        }
    }

    public void putProduct(Product product) throws IOException {
        RequestBody body = RequestBody.create(new Gson().toJson(product), JSON); // new
        Request request = new Request.Builder()
                .url(URL + "/product/")
                .put(body)
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }
    
    
    //find the category in api
    public List<Category> getCategoryList() throws IOException {
        List<Category> saida = new ArrayList();
        Request request = new Request.Builder()
                .url(URL + "/category/")
                .header("company_id", logonMB.getCompanyIdSession().getCompanyId())
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            // Get response body
            String json = response.body().string();
            System.out.println("Categorias: "+json);

            saida = new Gson().fromJson(json, new TypeToken<List<Category>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            saida = new ArrayList();
        }
        return saida;
    }

}
