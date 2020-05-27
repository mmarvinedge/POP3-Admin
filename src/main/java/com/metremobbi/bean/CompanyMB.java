/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.bean;

import com.metremobbi.model.Company;
import com.metremobbi.model.TimeOpen;
import com.metremobbi.model.dto.Bairro;
import com.metremobbi.service.CompanyService;
import com.metremobbi.util.ImageFile;
import com.metremobbi.util.Utils;
import static com.metremobbi.util.Utils.addDetailMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

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
    @Getter
    @Setter
    private List<Bairro> bairros = new ArrayList();
    @Getter
    @Setter
    private DualListModel<Bairro> dualBairros = new DualListModel<>();
    @Getter
    @Setter
    private BigDecimal taxa;
    @Getter
    @Setter
    private String bairroCadastro = "";

    public CompanyMB() {
        try {
            company = service.loadCompany(companyID);
            if (company.getTime() == null) {
                company.setTime(new TimeOpen());
            }
            loadBairros();
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

    public void save2() {
        try {
            company = service.saveCompany(company);
            addDetailMessage("Dados atualizados!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadBairros() {
        try {
            List<String> bairros = service.getBairros(company.getAddress().getCity());
            for (String bairro : bairros) {
                this.bairros.add(new Bairro(bairro));
            }

            List<Bairro> themesSource = this.bairros;

            List<Bairro> themesTarget = new ArrayList<Bairro>();
            if (company.getBairros() != null) {
                themesTarget.addAll(company.getBairros());
            }
            themesSource.removeAll(themesTarget);
            dualBairros = new DualListModel<Bairro>(themesSource, themesTarget);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onTransfer(TransferEvent event) {

        for (Object i : event.getItems()) {
            Bairro b = ((Bairro) i);
            if (event.isAdd()) {
                b.setTaxa(taxa == null ? BigDecimal.ZERO : taxa);
            } else {
                b.setTaxa(BigDecimal.ZERO);
            }
            System.out.println(b.getBairro() + " : " + b.getTaxa());
        }
    }

    public void confirmarRegioes() {
        company.setBairros(dualBairros.getTarget());
        save2();
    }

    public void cadastrarBairro() {
        try {

            Bairro b = service.cadastrarBairro(bairroCadastro, company.getAddress().getCity());
            b.setTaxa(BigDecimal.ZERO);
            if (b != null) {
                System.out.println("dualBairrosa.getSource(); " + dualBairros.getSource().size());
                dualBairros.getSource().add(b);
                Collections.sort(dualBairros.getSource(), (Bairro o1, Bairro o2) -> o1.getBairro().compareTo(o2.getBairro()));
                System.out.println("dualBairros.getSource(); " + dualBairros.getSource().size());
                addDetailMessage("Bairro adicionado!");
                PrimeFaces.current().executeScript("PF('dlgBairro').hide()");
            } else {
                addDetailMessage("Não foi possível cadastrar o bairro!", FacesMessage.SEVERITY_ERROR);
            }
        } catch (IOException e) {
            e.printStackTrace();
            addDetailMessage("Não foi possível cadastrar o bairro!", FacesMessage.SEVERITY_ERROR);
        }
    }

    public void uploadPhoto(FileUploadEvent event){
        File tempFile = null;
        try {
            tempFile = File.createTempFile("foto2", "png");
            InputStream is = event.getFile().getInputstream();
            FileOutputStream out = new FileOutputStream(tempFile);
            IOUtils.copy(is, out);

            String imageBase64 = ImageFile.encoder(tempFile.getAbsolutePath());
            System.out.println(imageBase64.length());
            company.setLogo(imageBase64);
        } catch (IOException e) {
            String msg = "Erro ao converter a imagem em base64";
            addDetailMessage(msg, FacesMessage.SEVERITY_ERROR);
            e.printStackTrace();
        } finally {
            tempFile.deleteOnExit();
        }
    }
}
