/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.service;

import com.google.gson.Gson;
import com.metremobbi.model.Company;
import com.metremobbi.util.Constantes;
import java.io.IOException;
import javax.faces.bean.ViewScoped;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author Renato
 */
@ViewScoped
public class CompanyService {

    OkHttpClient client = new OkHttpClient();

    public Company loadCompany(String idCompany) throws IOException, Exception {
        if (idCompany == null) {
            throw new Exception("NAO CHEGOU ID DA COMPANIA");
        }
        Company comp = new Company();
        Request request = new Request.Builder()
                .url(Constantes.URL + "/company/" + idCompany)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            // Get response body
            String json = response.body().string();

            comp = new Gson().fromJson(json, Company.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Erro Carai");
        }

        return comp;
    }

    public Company saveCompany(Company c) throws IOException, Exception {

        RequestBody body = RequestBody.create(new Gson().toJson(c), Constantes.JSON); // new
        // RequestBody body = RequestBody.create(JSON, json); // old
        Request request = new Request.Builder()
                .url(Constantes.URL + "/company/")
                .put(body)
                .build();
        Response response = client.newCall(request).execute();
        String b = response.body().string();
        Company a = new Gson().fromJson(b, Company.class);
        return a;
    }
}
