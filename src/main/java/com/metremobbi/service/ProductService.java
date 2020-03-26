/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.service;

import com.metremobbi.model.Product;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author PICHAU
 */
public class ProductService {
    
    private final OkHttpClient httpClient = new OkHttpClient();
    
    public List<Product> getProducts() throws IOException{
        List<Product> saida = new ArrayList();
        Request request = new Request.Builder()
                .url("localhost:4000/product/")
                .get()
                .header("company_id", "1")
                .build();
        
        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            System.out.println(response.body().string());
        }
        
        return saida;
    }
    
    public void postProduct(Product product) throws IOException {
        RequestBody productBody = new FormBody.Builder()
                .add("sku", product.getSku())
                .add("name", product.getName())
                .add("price", product.getPrice().toString())
                .add("category", product.getCategory().toString())
                .add("obs", product.getObs())
                .add("companyId", "1")
                .build();

        Request request = new Request.Builder()
                .url("http://localhost:4000/product/")
                .post(productBody)
                .build();
        
        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            System.out.println(response.body().string());
        }
    }
    
}
