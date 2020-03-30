/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.bean;

import com.metremobbi.model.User;
import com.metremobbi.service.UserService;
import static com.metremobbi.util.Utils.addDetailMessage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Márvin Edge
 */
@ManagedBean
@ViewScoped
public class UserMB implements Serializable {

    @Getter
    @Setter
    private UserService service;
    @Getter
    @Setter
    private User user;
    @Getter
    @Setter
    private List<User> users;
    @Getter
    @Setter
    private List<User> usersSelected;
    LazyDataModel<User> productLazy;
    @Getter
    @Setter
    List<User> filteredValue;

    public UserMB() {
        service = new UserService();
        user = new User();
        users = new ArrayList();
        usersSelected = new ArrayList();
    }

    @PostConstruct
    public void init() {
        try {
            get();
        } catch (Exception e) {
            e.printStackTrace();
            users = new ArrayList();
        }
    }

    public void get() {
        users = service.getUsers();
    }

    public void novo() {
        user = new User();
    }

    public void save() {
        if (user.getId() == null) {
            try {
                user.setCompanyId(service.idCompany);
                service.postUser(user);
                System.out.println("Usuário " + user.getName() + " inserido com sucesso!");
                addDetailMessage("Usuário inserido com sucesso.");
                users.add(user);
                novo();
            } catch (Exception e) {
                e.printStackTrace();
                addDetailMessage("Erro ao inserir usuário!", FacesMessage.SEVERITY_ERROR);
            }
        } else {
            try {
                service.putUser(user);
                System.out.println("Erro ao atualizar o usuário " + user.getName());
                addDetailMessage("Usuário atualizado com sucesso!");
                novo();
            } catch (Exception e) {
                e.printStackTrace();
                addDetailMessage("Erro ao atualizar usuário!", FacesMessage.SEVERITY_ERROR);
            }
        }
    }

    public void delete() throws IOException {
        Integer size = users.size();
        service.deleteUser(usersSelected);
        if (size > 1) {
            addDetailMessage("Usuários deletados com sucesso.");
        } else {
            addDetailMessage("Usuário deletado com sucesso.");
        }
        users.remove(usersSelected);
    }

}