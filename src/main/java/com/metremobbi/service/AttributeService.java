/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.metremobbi.model.Attribute;
import com.metremobbi.model.AttributeValue;
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
public class AttributeService {

    private final String companyID = Utils.usuarioLogado().getCompanyId();

    OkHttpClient client = new OkHttpClient();

    public Attribute postAttribute(Attribute attribute) throws IOException {
        System.out.println(Constantes.URL);
        attribute.setCompanyId(companyID);
        RequestBody body = RequestBody.create(new Gson().toJson(attribute), Constantes.JSON); // new
        // RequestBody body = RequestBody.create(JSON, json); // old
        Request request = new Request.Builder()
                .url(Constantes.URL + "/attribute/")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        String b = response.body().string();
        Attribute a = new Gson().fromJson(b, Attribute.class);
        return a;
    }

    public void deleteAttribute(Attribute attribute) throws IOException {
        RequestBody body = RequestBody.create(new Gson().toJson(attribute), Constantes.JSON); // new
        Request request = new Request.Builder()
                .url(Constantes.URL + "/attribute/")
                .delete(body)
                .build();
        System.out.println("vou deletar o atributo " + attribute.getName());
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }

    public Attribute putAttribute(Attribute attribute) throws IOException {
        System.out.println(Constantes.URL);
        attribute.setCompanyId(companyID);
        RequestBody body = RequestBody.create(new Gson().toJson(attribute), Constantes.JSON); // new
        // RequestBody body = RequestBody.create(JSON, json); // old
        Request request = new Request.Builder()
                .url(Constantes.URL + "/attribute/")
                .put(body)
                .build();
        Response response = client.newCall(request).execute();
        String b = response.body().string();
        Attribute a = new Gson().fromJson(b, Attribute.class);
        return a;
    }

}
