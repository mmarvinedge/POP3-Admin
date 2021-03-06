/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.metremobbi.model.Category;
import com.metremobbi.model.Product;
import com.metremobbi.util.Constantes;
import com.metremobbi.util.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ViewScoped;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 *
 * @author PICHAU
 */
@ViewScoped
public class ProductService {

    private final String companyID = Utils.usuarioLogado().getCompanyId();

    OkHttpClient client = new OkHttpClient();

    private final OkHttpClient httpClient = new OkHttpClient();

    public List<Product> getProducts() throws IOException {
        List<Product> saida = new ArrayList();
        Request request = new Request.Builder()
                .url(Constantes.URL + "/product/")
                .header("company_id", companyID)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            // Get response body
            String json = response.body().string();

            saida = new Gson().fromJson(json, new TypeToken<List<Product>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            saida = new ArrayList();
        }
        return saida;
    }

    public Product getProduct(Product product) {
        Product saida = new Product();
        Request request = new Request.Builder()
                .url(Constantes.URL + "/product/" + product.getId())
                .header("company_id", companyID)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String json = response.body().string();
            saida = new Gson().fromJson(json, Product.class);
        } catch (Exception e) {
            e.printStackTrace();
            saida = new Product();
        }
        return saida;
    }

    public Integer postProduct(Product product) {
        product.setCompanyId(companyID);
        product.setCategories(new ArrayList());
        product.getCategories().add(product.getCategoryMain());
//        System.out.println("JSON: "+new Gson().toJson(product));
        RequestBody body = RequestBody.create(new Gson().toJson(product), Constantes.JSON); // new
        // RequestBody body = RequestBody.create(JSON, json); // old
        Request request = new Request.Builder()
                .url(Constantes.URL + "/product/")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {;
            System.out.println(response.code());
            return response.code();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteProduct(List<Product> products) throws IOException {
        for (Product p : products) {
            RequestBody body = RequestBody.create(new Gson().toJson(p), Constantes.JSON); // new
            Request request = new Request.Builder()
                    .url(Constantes.URL + "/product/")
                    .delete(body)
                    .build();
            Response response = client.newCall(request).execute();
            String json = response.body().string();
        }
    }

    public Integer putProduct(Product product) {
        RequestBody body = RequestBody.create(new Gson().toJson(product), Constantes.JSON); // new
        Request request = new Request.Builder()
                .url(Constantes.URL + "/product/")
                .put(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.code();
        } catch (Exception e) {
            return null;
        }
    }

    //find the category in api
    public List<Category> getCategoryList() throws IOException {
        List<Category> saida = new ArrayList();
        Request request = new Request.Builder()
                .url(Constantes.URL + "/category/")
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            // Get response body
            String json = response.body().string();
            saida = new Gson().fromJson(json, new TypeToken<List<Category>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            saida = new ArrayList();
        }
        return saida;
    }

    public void updatecategorys() throws IOException {
        Request request = new Request.Builder()
                .url(Constantes.URL + "/product/updateCategory/")
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disableProducts(Category category, String situation) throws IOException {
        Request request = new Request.Builder()
                .url(Constantes.URL + "/product/disableProducts/" + category.getName() + "/" + situation)
                .header("company_id", companyID)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
