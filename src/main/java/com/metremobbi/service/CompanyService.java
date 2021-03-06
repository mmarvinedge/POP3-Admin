/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.metremobbi.model.Company;
import com.metremobbi.model.dto.Bairro;
import com.metremobbi.util.Constantes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
        System.out.println("JSON: "+new Gson().toJson(c));
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

    public List<String> getBairros(String city) throws IOException {
        List<String> saida = new ArrayList();
        Request request = new Request.Builder()
                .url("http://metre.ddns.net:88/bairro.php?cidade=" + city)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() == 202) {
            throw new IOException("Nenhum dado retornado!");
        } else {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
        }

        // Get response body
        String json = response.body().string();
        List<Bairro> bairs = new Gson().fromJson(json, new TypeToken<List<Bairro>>() {
        }.getType());
        for (Bairro bair : bairs) {
            saida.add(bair.getBairro());
        }
        Collections.sort(saida, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        return saida;
    }

    public Bairro cadastrarBairro(String bairro, String cidade) throws IOException {
        Request request = new Request.Builder()
                .url("http://metre.ddns.net:88/cadastrar-bairro.php?bairro=" + bairro + "&cidade=" + cidade)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        if (response.code() == 202) {
            throw new IOException("Nenhum dado retornado!");
        } else {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
        }

        // Get response body
        String json = response.body().string();
        Bairro b = new Gson().fromJson(json, Bairro.class);

        return b;
    }
}
