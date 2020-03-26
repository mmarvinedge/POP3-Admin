/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.service;

import com.google.gson.Gson;
import com.metremobbi.model.Product;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.FormBody;
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

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    public static final String URL = "http://localhost:4000";

    public static final String idCompany = "5e56b4471c9d4400008ecda2";

    private final OkHttpClient httpClient = new OkHttpClient();

    public List<Product> getProducts() throws IOException {
        List<Product> saida = new ArrayList();
        Request request = new Request.Builder()
                .url("localhost:4000/product/")
                .get()
                .header("company_id", idCompany)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            // Get response body
            System.out.println(response.body().string());
        }
        return saida;
    }

    public void postProduct(Product product) throws IOException {
        RequestBody body = RequestBody.create(new Gson().toJson(product), JSON); // new
        // RequestBody body = RequestBody.create(JSON, json); // old
        Request request = new Request.Builder()
                .url(URL + "/product/save")
                .header("company_id", idCompany)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }

}
