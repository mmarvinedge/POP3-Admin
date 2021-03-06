/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.metremobbi.model.User;
import com.metremobbi.util.Constantes;
import com.metremobbi.util.Utils;
import java.io.IOException;
import java.net.ProtocolException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author PICHAU
 */
public class UserService {

    private final OkHttpClient httpClient = new OkHttpClient();

    public User login(User user) throws IOException, NoSuchAlgorithmException {
        User u = new User();
        RequestBody body = RequestBody.create(new Gson().toJson(user), Constantes.JSON); // new
        Request request = new Request.Builder()
                .url(Constantes.URL + "/user/login")
                .post(body)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            System.out.println("codeeeeeeeeeeee " + response.code());

            //Cliente com licença expirada
            if (response.code() == 408) {
                User block = new User();
                block.setName("expired");
                return block;
            }

            // Cliente está bloqueado
            if (response.code() == 409) {
                User b = new User();
                b.setName("block");
                return b;
            }

            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String retorno = response.body().string();
            u = new Gson().fromJson(retorno, User.class);
            System.out.println(retorno);
            return u;
        } catch (ProtocolException e) {
            User uu = new User();
            uu.setName("trialexpired");
            System.out.println("entrei no 407");
            return uu;
        } catch (IOException e) {
            return u;
        }
    }

    public List<User> getUsers() {
        List<User> saida = new ArrayList();
        Request request = new Request.Builder()
                .url(Constantes.URL + "/user/")
                .header("company_id", Utils.usuarioLogado().getCompanyId())
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            // Get response body
            String json = response.body().string();
            saida = new Gson().fromJson(json, new TypeToken<List<User>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            saida = new ArrayList();
        }
        return saida;
    }

    public void postUser(User user) throws IOException {
        user.setCompanyId(Utils.usuarioLogado().getCompanyId());
        RequestBody body = RequestBody.create(new Gson().toJson(user), Constantes.JSON); // new
        // RequestBody body = RequestBody.create(JSON, json); // old
        Request request = new Request.Builder()
                .url(Constantes.URL + "/user/")
                .post(body)
                .build();
        Response response = httpClient.newCall(request).execute();
        String json = response.body().string();
    }

    public void deleteUser(List<User> users) throws IOException {
        for (User u : users) {
            RequestBody body = RequestBody.create(new Gson().toJson(u), Constantes.JSON); // new
            Request request = new Request.Builder()
                    .url(Constantes.URL + "/user/")
                    .delete(body)
                    .build();
            Response response = httpClient.newCall(request).execute();
            String json = response.body().string();
        }
    }

    public void putUser(User user) throws IOException {
        RequestBody body = RequestBody.create(new Gson().toJson(user), Constantes.JSON); // new
        Request request = new Request.Builder()
                .url(Constantes.URL + "/user/")
                .put(body)
                .build();
        Response response = httpClient.newCall(request).execute();
        String json = response.body().string();
    }

    public User findUser(String username) {
        User saida = new User();
        Request request = new Request.Builder()
                .url(Constantes.URL + "/user/finduser/" + username)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            // Get response body
            String json = response.body().string();
            saida = new Gson().fromJson(json, new TypeToken<User>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            saida = new User();
        }
        return saida;
    }
}
