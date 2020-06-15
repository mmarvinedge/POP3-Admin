/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.metremobbi.model.CouponCode;
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

/**
 *
 * @author Marvin
 */
@ViewScoped
public class CouponService {
    
    OkHttpClient client = new OkHttpClient();

    private final OkHttpClient httpClient = new OkHttpClient();
    
    private final String companyID = Utils.usuarioLogado().getCompanyId();
    
    public List<CouponCode> getCouponCodes() {
        List<CouponCode> saida = new ArrayList();
        Request request = new Request.Builder()
                .url(Constantes.URL + "/coupon/")
                .header("company_id", companyID)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            // Get response body
            String json = response.body().string();
            saida = new Gson().fromJson(json, new TypeToken<List<CouponCode>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            saida = new ArrayList();
        }
        return saida;
    }

    public void postCouponCode(CouponCode coupon) {
        try {
        coupon.setEnable(Boolean.TRUE);
        RequestBody body = RequestBody.create(new Gson().toJson(coupon), Constantes.JSON); // new
        // RequestBody body = RequestBody.create(JSON, json); // old
        Request request = new Request.Builder()
                .url(Constantes.URL + "/coupon/")
                .header("company_id", companyID)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        String json = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCouponCode(List<CouponCode> users) throws IOException {
        for (CouponCode u : users) {
            RequestBody body = RequestBody.create(new Gson().toJson(u), Constantes.JSON); // new
            Request request = new Request.Builder()
                    .url(Constantes.URL + "/coupon/")
                    .delete(body)
                    .build();
            Response response = client.newCall(request).execute();
            String json = response.body().string();
        }
    }

    public void putCouponCode(CouponCode user) throws IOException {
        RequestBody body = RequestBody.create(new Gson().toJson(user), Constantes.JSON); // new
        Request request = new Request.Builder()
                .url(Constantes.URL + "/coupon/")
                .put(body)
                .build();
        Response response = client.newCall(request).execute();
        String json = response.body().string();
    }
}
