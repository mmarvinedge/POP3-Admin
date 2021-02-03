/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.metremobbi.model.Order;
import com.metremobbi.model.Product;
import com.metremobbi.model.dto.ThreeParameters;
import com.metremobbi.model.filter.OrderFilter;
import com.metremobbi.util.Constantes;
import com.metremobbi.util.OUtils;
import com.metremobbi.util.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * @author Márvin Edge
 */
public class OrderService {

    Gson gson = new GsonBuilder().setDateFormat("yyy-MM-dd HH:mm:ss").create();

    private final String companyID = Utils.usuarioLogado().getCompanyId();

    OkHttpClient client = new OkHttpClient();

    private final OkHttpClient httpClient = new OkHttpClient();

    public List<Order> getOrdersDay() throws IOException {
        List<Order> saida = new ArrayList();
        Request request = new Request.Builder()
                .url(Constantes.URL + "/order/day")
                .header("company_id", companyID)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() == 200) {
                // Get response body
                String json = response.body().string();
                if (!json.trim().isEmpty()) {
                    saida = gson.fromJson(json, new TypeToken<List<Order>>() {
                    }.getType());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            saida = new ArrayList();
        }
        return saida;
    }

    public List<Order> getOrdersWeek() throws IOException {
        List<Order> saida = new ArrayList();
        Request request = new Request.Builder()
                .url(Constantes.URL + "/order/week")
                .header("company_id", companyID)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() == 200) {
                // Get response body
                String json = response.body().string();
                if (!json.trim().isEmpty()) {
                    saida = gson.fromJson(json, new TypeToken<List<Order>>() {
                    }.getType());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            saida = new ArrayList();
        }
        return saida;
    }

    public List<Order> pesquisar(OrderFilter filtro) throws IOException {
        List<Order> saida = new ArrayList();
        for (Date data : filtro.getDatas()) {
            System.out.println(OUtils.formataData(data, "dd/MM/yyyy"));
        }
        System.out.println("STARTW: " + OUtils.dateToLocal(filtro.getDatas().get(0)));
        System.out.println("ENDW  : " + OUtils.dateToLocal(filtro.getDatas().get(1)));
        Request request = new Request.Builder()
                .url(Constantes.URL + "/order/pesquisar/filtro")
                .header("company_id", companyID)
                .header("start", OUtils.dateToLocal(filtro.getDatas().get(0)).toString())
                .header("end", OUtils.dateToLocal(filtro.getDatas().get(1)).toString())
                .header("order", filtro.getPedido() == null ? "" : filtro.getPedido())
                .header("phone", filtro.getPhone() == null ? "" : filtro.getPhone())
                .header("status", filtro.getSituation() == null ? "" : filtro.getSituation())
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() == 200) {
                // Get response body
                String json = response.body().string();
                if (!json.trim().isEmpty()) {
                    saida = gson.fromJson(json, new TypeToken<List<Order>>() {
                    }.getType());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            saida = new ArrayList();
        }
        return saida;
    }

    public List<Order> pesquisar(Date ini, Date fim) throws IOException {
        List<Order> saida = new ArrayList();

        Request request = new Request.Builder()
                .url(Constantes.URL + "/order/pesquisar/filtro")
                .header("company_id", companyID)
                .header("start", OUtils.dateToLocal(ini).toString())
                .header("end", OUtils.dateToLocal(fim).toString())
                .header("order", "")
                .header("phone", "")
                .header("status", "")
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() == 200) {
                // Get response body
                String json = response.body().string();
                if (!json.trim().isEmpty()) {
                    saida = gson.fromJson(json, new TypeToken<List<Order>>() {
                    }.getType());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            saida = new ArrayList();
        }
        return saida;
    }

    public List<Order> getOrdersMonth() throws IOException {
        List<Order> saida = new ArrayList();
        Request request = new Request.Builder()
                .url(Constantes.URL + "/order/month")
                .header("company_id", companyID)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() == 200) {
                // Get response body
                String json = response.body().string();
                if (!json.trim().isEmpty()) {
                    saida = gson.fromJson(json, new TypeToken<List<Order>>() {
                    }.getType());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            saida = new ArrayList();
        }
        return saida;
    }

    public List<ThreeParameters> getOrderStatus(Date ini, Date fim) throws IOException {
        List<ThreeParameters> saida = new ArrayList();
        Request request = new Request.Builder()
                .url(Constantes.URL + "/order/qntpedidosporstatus")
                .header("company_id", companyID)
                .header("start", OUtils.dateToLocal(ini).toString())
                .header("end", OUtils.dateToLocal(fim).toString())
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() == 200) {
                // Get response body
                String json = response.body().string();
                if (!json.trim().isEmpty()) {
                    saida = gson.fromJson(json, new TypeToken<List<ThreeParameters>>() {
                    }.getType());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            saida = new ArrayList();
        }
        return saida;
    }

    public List<Product> listarFotosDeProdutos() throws IOException {
        List<Product> saida = new ArrayList();
        Request request = new Request.Builder()
                .url(Constantes.URL + "/product/fotos")
                .header("company_id", companyID)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() == 200) {
                // Get response body
                String json = response.body().string();
                if (!json.trim().isEmpty()) {
                    saida = gson.fromJson(json, new TypeToken<List<Product>>() {
                    }.getType());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            saida = new ArrayList();
        }
        return saida;
    }

    public List<Object> getTopClientes(Date ini, Date fim) throws IOException {
        List<Object> saida = new ArrayList();
        Request request = new Request.Builder()
                .url(Constantes.URL + "/order/topclientes")
                .header("company_id", companyID)
                .header("start", OUtils.dateToLocal(ini).toString())
                .header("end", OUtils.dateToLocal(fim).toString())
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() == 200) {
                // Get response body
                String json = response.body().string();
                if (!json.trim().isEmpty()) {
                    saida = gson.fromJson(json, new TypeToken<List<Object>>() {
                    }.getType());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            saida = new ArrayList();
        }
        return saida;
    }

    public List<Order> listOrderByPhone(String phone) throws IOException, Exception {
        System.out.println("ORDER BY FONE");
        String url = Constantes.URL + "/order/allbyphone/" + phone;
        System.out.println("URL: " + url);
        List<Order> orders = new ArrayList();
        Request request = new Request.Builder()
                .url(url)
                .header("company_id", companyID)
                .get()
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            // Get response body
            String json = response.body().string();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            orders = gson.fromJson(json, new TypeToken<List<Order>>() {
            }.getType());

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Erro Carai");
        }
        return orders;

    }

}
