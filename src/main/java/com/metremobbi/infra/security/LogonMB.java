package com.metremobbi.infra.security;

import com.github.adminfaces.template.session.AdminSession;
import org.omnifaces.util.Faces;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

import static com.metremobbi.util.Utils.addDetailMessage;
import com.github.adminfaces.template.config.AdminConfig;
import com.metremobbi.model.User;
import com.metremobbi.service.UserService;
import java.security.NoSuchAlgorithmException;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by rmpestano on 12/20/14.
 *
 * This is just a login example.
 *
 * AdminSession uses isLoggedIn to determine if user must be redirect to login
 * page or not. By default AdminSession isLoggedIn always resolves to true so it
 * will not try to redirect user.
 *
 * If you already have your authorization mechanism which controls when user
 * must be redirect to initial page or logon you can skip this class.
 */
@Named
@SessionScoped
@Specializes
public class LogonMB extends AdminSession implements Serializable {

    @Getter
    @Setter
    private User userLogin;
    @Getter
    @Setter
    private User currentUser;
    private boolean remember;
    @Inject
    private AdminConfig adminConfig;
    @Getter
    @Setter
    private UserService service;

    public LogonMB() {
        service = new UserService();
        userLogin = new User();
        currentUser = null;
    }

    public void login() throws IOException, NoSuchAlgorithmException {
        currentUser = new User();
        currentUser = service.login(userLogin);
        if (currentUser.getUserName() != null) {
            addDetailMessage("Bem vindo(a) <b>" + currentUser.getName() + "</b>");
            Faces.getExternalContext().getFlash().setKeepMessages(true);
            Faces.redirect(adminConfig.getIndexPage());
        } else {
            addDetailMessage("Login ou senha inválidos, tente novamente!", FacesMessage.SEVERITY_ERROR);
            Faces.validationFailed();
            currentUser = null;
        }
    }

    @Override
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

}
