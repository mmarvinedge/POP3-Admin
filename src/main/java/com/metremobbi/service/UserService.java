/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.service;

import com.google.gson.Gson;
import com.metremobbi.model.User;
import static com.metremobbi.util.Utils.addDetailMessage;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.faces.application.FacesMessage;
import javax.xml.bind.DatatypeConverter;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author PICHAU
 */
public class UserService {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    public static final String URL = "http://localhost:4000";

    public static final String idCompany = "5e56b4471c9d4400008ecda2";

    private final OkHttpClient httpClient = new OkHttpClient();

    public User login(User user) throws IOException, NoSuchAlgorithmException {
        User u = new User();
        RequestBody body = RequestBody.create(new Gson().toJson(user), JSON); // new
        Request request = new Request.Builder()
                .url(URL + "/user/login")
                .post(body)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            u = new Gson().fromJson(response.body().string(), User.class);
            return u;
        } catch (Exception e) {
            e.printStackTrace();
            return u;
        }
    }
}
