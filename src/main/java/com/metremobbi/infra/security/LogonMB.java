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
import com.metremobbi.model.Company;
import com.metremobbi.model.User;
import com.metremobbi.service.CompanyService;
import com.metremobbi.service.UserService;
import com.metremobbi.util.DateUtil;
import com.metremobbi.util.OUtils;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    @Getter
    @Setter
    private Company company;
    private boolean remember;
    @Inject
    private AdminConfig adminConfig;
    @Getter
    @Setter
    private UserService service;
    @Getter
    @Setter
    private CompanyService companyService;

    public LogonMB() {
        service = new UserService();
        userLogin = new User();
        currentUser = null;
        company = new Company();
        companyService = new CompanyService();
    }

    public void login() throws IOException, NoSuchAlgorithmException, Exception {
        currentUser = new User();
        currentUser = service.login(userLogin);
        System.out.println(currentUser);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", currentUser);
        if (currentUser.getName() != null) {
            if (currentUser.getName().equalsIgnoreCase("trialexpired")) {
                addMessage("Seu período teste de 15 dias encerrou, para continuar utilizando entre em contato com seu agente de vendas!");
                Faces.validationFailed();
                currentUser = null;
            } else if (currentUser.getName().equalsIgnoreCase("expired")) {
                addMessage("Sua licença expirou, para renovar acesse nosso site.");
                Faces.validationFailed();
                currentUser = null;
            } else if (currentUser.getName().equalsIgnoreCase("block")) {
                addMessage("Acesso negado!\nEntre em contato com o suporte.");
                Faces.validationFailed();
                currentUser = null;
            } else {
                OUtils.setUser(currentUser);
                company = companyService.loadCompany(currentUser.getCompanyId());
                addDetailMessage("Bem vindo(a) <b>" + currentUser.getName() + "</b>");
                Faces.getExternalContext().getFlash().setKeepMessages(true);
                Faces.redirect(adminConfig.getIndexPage());
            }
        } else {
            addMessage("Login ou senha inválidos, tente novamente!");
            Faces.validationFailed();
            currentUser = null;
        }
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
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
