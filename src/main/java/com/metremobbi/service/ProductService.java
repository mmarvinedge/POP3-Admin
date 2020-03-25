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
    
    public List<Product> getProducts(){
        List<Product> saida = new ArrayList();
        String jsonProducts;
        
        
        return saida;
    }
    
    public void postProduct(Product product) throws IOException {
        RequestBody productBody = new FormBody.Builder()
                .add("sku", product.getSku())
                .add("name", product.getName())
                .add("price", product.getPrice().toString())
                .add("category", product.getCategory().getDescrition())
                .add("obs", product.getObs())
                .build();

        Request request = new Request.Builder()
                .url("https://localhost:4000/product/")
                .addHeader("User-Agent", "OkHttp Bot")
                .post(productBody)
                .build();
        
        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            System.out.println(response.body().string());
        }
    }
    
}
