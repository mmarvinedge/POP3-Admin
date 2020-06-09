/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.bean;

import com.metremobbi.model.Company;
import com.metremobbi.model.CouponCode;
import com.metremobbi.service.CompanyService;
import com.metremobbi.service.CouponService;
import com.metremobbi.util.Utils;
import static com.metremobbi.util.Utils.addDetailMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Marvin
 */
@ManagedBean(eager = true)
@ViewScoped
public class CouponMB {

    @Getter
    @Setter
    private CouponService service;
    @Getter
    @Setter
    private CompanyService companyService;
    @Getter
    @Setter
    private CouponCode coupon;
    @Getter
    @Setter
    private CompanyMB companyMB;
    @Getter
    @Setter
    private List<CouponCode> coupons;
    @Getter
    @Setter
    private List<CouponCode> couponsSelected;

    @PostConstruct
    public void init() {
        try {
            coupons = new ArrayList();
            couponsSelected = new ArrayList();
            service = new CouponService();
            companyService = new CompanyService();
            coupon = new CouponCode();
            get();
        } catch (Exception e) {
            e.printStackTrace();
            coupons = new ArrayList();
        }
    }

    public void get() throws Exception {
        Company company = companyService.loadCompany(Utils.usuarioLogado().getCompanyId());
        coupons = company.getCoupons();
    }

    public void novo() {
        coupon = new CouponCode();
    }

    public void save() {
        if (coupon.getId() == null) {
            try {
                coupon.setEnable(Boolean.TRUE);
                service.postCouponCode(coupon);
                coupons.add(coupon);
                addDetailMessage("Cupom inserido com sucesso.");
                novo();
            } catch (Exception e) {
                e.printStackTrace();
                addDetailMessage("Erro ao inserir cupom!", FacesMessage.SEVERITY_ERROR);
            }
        } else {
            try {
                service.putCouponCode(coupon);
                System.out.println("CUPOM ATUALIZADO COM SUCESSO " + coupon.getSlug());
                addDetailMessage("Cupom atualizado com sucesso!");
                novo();
            } catch (Exception e) {
                e.printStackTrace();
                addDetailMessage("Erro ao atualizar cupom!", FacesMessage.SEVERITY_ERROR);
            }
        }
    }

    public void delete() throws IOException {
        Integer size = coupons.size();
        service.deleteCouponCode(couponsSelected);
        coupons.remove(couponsSelected);
        if (size > 1) {
            addDetailMessage("Cupons deletados com sucesso.");
        } else {
            addDetailMessage("Cupom deletado com sucesso.");
        }
    }

    public void update(CouponCode c) throws IOException {
        service.putCouponCode(c);
    }

}
