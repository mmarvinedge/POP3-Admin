/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.bean;

import com.metremobbi.model.Company;
import com.metremobbi.model.TimeOpen;
import com.metremobbi.service.AttributeService;
import com.metremobbi.service.CompanyService;
import com.metremobbi.util.Utils;
import static com.metremobbi.util.Utils.addDetailMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Renato
 */
@ManagedBean(eager = true)
@ViewScoped
public class CompanyMB {

    CompanyService service = new CompanyService();

    @Getter
    @Setter
    private Company company = new Company();
    @Getter
    @Setter
    private String companyID = Utils.usuarioLogado().getCompanyId();

    public CompanyMB() {
        try {
            company = service.loadCompany(companyID);
            if (company.getTime() == null) {
                company.setTime(new TimeOpen());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {

            company = service.saveCompany(company);
            addDetailMessage("Horários atualizados!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
