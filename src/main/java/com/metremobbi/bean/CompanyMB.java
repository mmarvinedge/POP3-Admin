/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.bean;

import com.metremobbi.infra.model.BairroDataModel;
import com.metremobbi.model.Company;
import com.metremobbi.model.Shift;
import com.metremobbi.model.TimeOpen;
import com.metremobbi.model.dto.Bairro;
import com.metremobbi.service.CompanyService;
import com.metremobbi.util.ImageFile;
import com.metremobbi.util.OUtils;
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
import java.util.stream.Collectors;
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
    private BairroDataModel bairroModel;
    @Getter
    @Setter
    private DualListModel<Bairro> dualBairros = new DualListModel<>();
    @Getter
    @Setter
    private BigDecimal taxa;
    @Getter
    @Setter
    private Boolean taxaUnicaEntrega;
    @Getter
    @Setter
    private String bairroCadastro = "";

    public CompanyMB() {
        try {
            System.out.println("ID DA COMPANY: " + companyID);
            company = service.loadCompany(companyID);
            try {
                taxaUnicaEntrega = company.getDeliveryCost().doubleValue() != 0;
            } catch (Exception ex) {
                System.err.println("Erro ao carregar delivery cost");
            }

            if (company.getTime() == null) {
                company.setTime(new TimeOpen());
            }
            if (company.getShift() == null) {
                company.setShift(new Shift());
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
            if (company.getBairros() != null && !company.getBairros().isEmpty() && company.getBairros().size() > 0) {
                List<String> bairrosMetre = service.getBairros(company.getAddress().getCity());
                if (company.getBairros().size() < bairrosMetre.size()) {
                    for (String b : bairrosMetre) {
                        if (company.getBairros().stream().filter(c -> c.getBairro().equalsIgnoreCase(b)).collect(Collectors.toList()).size() == 0) {
                            company.getBairros().add(new Bairro(b));
                        }
                    }
                }
                bairros = company.getBairros();
            } else {
                List<String> bairros = service.getBairros(company.getAddress().getCity());
                System.out.println("VEIO: " + bairros.size());
                for (String bairro : bairros) {
                    this.bairros.add(new Bairro(bairro));
                }

                for (Bairro bairro : this.bairros) {
                    Bairro b = company.getBairros().stream().filter(p -> p.getBairro().equalsIgnoreCase(bairro.getBairro())).findFirst().orElse(null);
                    if (b != null) {
                        System.out.println("BAIRRO: " + b.getBairro());
                        bairro.setEntrega(true);
                        bairro.setTaxa(b.getTaxa());
                    }
                }
            }
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
        try {
            company.setBairros(bairros);
            service.saveCompany(company);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cadastrarBairro() {
        try {
            if (!dualBairros.getSource().stream().filter(f -> f.getBairro().equalsIgnoreCase(bairroCadastro.trim())).collect(Collectors.toList()).isEmpty()) {
                addDetailMessage("Bairro já cadastrado!", FacesMessage.SEVERITY_ERROR);
                return;
            }
            Bairro b = service.cadastrarBairro(bairroCadastro, company.getAddress().getCity());
            b.setTaxa(BigDecimal.ZERO);
            if (b != null) {
                System.out.println("dualBairrosa.getSource(); " + dualBairros.getSource().size());
                dualBairros.getSource().add(b);
                Collections.sort(dualBairros.getSource(), (Bairro o1, Bairro o2) -> o1.getBairro().compareTo(o2.getBairro()));
                System.out.println("dualBairros.getSource(); " + dualBairros.getSource().size());
                addDetailMessage("Bairro adicionado!");
                PrimeFaces.current().executeScript("PF('dlgBairro').hide()");
                bairroCadastro = null;
            } else {
                addDetailMessage("Não foi possível cadastrar o bairro!", FacesMessage.SEVERITY_ERROR);
            }
            bairroCadastro = "";
        } catch (IOException e) {
            e.printStackTrace();
            addDetailMessage("Não foi possível cadastrar o bairro!", FacesMessage.SEVERITY_ERROR);
        }
    }

    public void uploadPhoto(FileUploadEvent event) {
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

    public void updateDeliveryCostType(Boolean b) throws Exception {
        company.setUniqueDeliveryCost(b);
        Company c = company;
        service.saveCompany(c);
        System.out.println(c.getUniqueDeliveryCost());
    }

    public String calcExpiredLicese() {
        return OUtils.formataData(OUtils.addMes(OUtils.getDataByTexto(company.getLicenseDate(), "yyyy-MM-dd"), company.getLicenseType()), "dd/MM/yyyy");
    }

    public String calcExpiredTrial() {
        return OUtils.formataData(OUtils.addDia(OUtils.getDataByTexto(company.getTrialDate(), "yyyy-MM-dd"), 15), "dd/MM/yyyy");
    }

    public void debugEntrega(Bairro b) {
        System.out.println(b.getEntrega());
    }

    public Boolean validaHorarios(Company c) {
        if (c.getTime() != null) {
            if (c.getTime().getSeg() && Integer.parseInt(c.getTime().getCloseSeg()) < Integer.parseInt(c.getTime().getOpenSeg())) {
                return false;
            } else if (c.getTime().getTer() && Integer.parseInt(c.getTime().getCloseTer()) < Integer.parseInt(c.getTime().getOpenTer())) {
                return false;
            } else if (c.getTime().getQua() && Integer.parseInt(c.getTime().getCloseQua()) < Integer.parseInt(c.getTime().getOpenQua())) {
                return false;
            } else if (c.getTime().getQui() && Integer.parseInt(c.getTime().getCloseQui()) < Integer.parseInt(c.getTime().getOpenQui())) {
                return false;
            } else if (c.getTime().getSex() && Integer.parseInt(c.getTime().getCloseSex()) < Integer.parseInt(c.getTime().getOpenSex())) {
                return false;
            } else if (c.getTime().getSab() && Integer.parseInt(c.getTime().getCloseSab()) < Integer.parseInt(c.getTime().getOpenSab())) {
                return false;
            } else if (c.getTime().getDom() && Integer.parseInt(c.getTime().getCloseDom()) < Integer.parseInt(c.getTime().getOpenDom())) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

}
