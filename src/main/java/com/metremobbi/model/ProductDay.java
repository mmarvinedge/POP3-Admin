/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.model;

/**
 *
 * @author Renato
 */
public class ProductDay {

    private Boolean naoVenderDom;
    private Boolean naoVenderSeg;
    private Boolean naoVenderTer;
    private Boolean naoVenderQua;
    private Boolean naoVenderQui;
    private Boolean naoVenderSex;
    private Boolean naoVenderSab;

    public ProductDay() {
        naoVenderDom = false;
        naoVenderSeg = false;
        naoVenderTer = false;
        naoVenderQua = false;
        naoVenderQui = false;
        naoVenderSex = false;
        naoVenderSab = false;
    }

    public Boolean getNaoVenderDom() {
        return naoVenderDom;
    }

    public void setNaoVenderDom(Boolean naoVenderDom) {
        this.naoVenderDom = naoVenderDom;
    }

    public Boolean getNaoVenderSeg() {
      
        return naoVenderSeg;
    }

    public void setNaoVenderSeg(Boolean naoVenderSeg) {
        this.naoVenderSeg = naoVenderSeg;
    }

    public Boolean getNaoVenderTer() {
       
        return naoVenderTer;
    }

    public void setNaoVenderTer(Boolean naoVenderTer) {
        this.naoVenderTer = naoVenderTer;
    }

    public Boolean getNaoVenderQua() {
      
        return naoVenderQua;
    }

    public void setNaoVenderQua(Boolean naoVenderQua) {
        this.naoVenderQua = naoVenderQua;
    }

    public Boolean getNaoVenderQui() {
        
        return naoVenderQui;
    }

    public void setNaoVenderQui(Boolean naoVenderQui) {
        this.naoVenderQui = naoVenderQui;
    }

    public Boolean getNaoVenderSex() {
        
        return naoVenderSex;
    }

    public void setNaoVenderSex(Boolean naoVenderSex) {
        this.naoVenderSex = naoVenderSex;
    }

    public Boolean getNaoVenderSab() {
        
        return naoVenderSab;
    }

    public void setNaoVenderSab(Boolean naoVenderSab) {
        this.naoVenderSab = naoVenderSab;
    }

}
