/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.model;

/**
 *
 * @author Marvin
 */
public class Shift {

    private Boolean morning;
    private Boolean afternoon;
    private Boolean night;

    public Boolean getMorning() {
        if (morning == null) {
            return Boolean.FALSE;
        } else {
            return morning;
        }
    }

    public void setMorning(Boolean morning) {
        this.morning = morning;
    }

    public Boolean getAfternoon() {
        if (afternoon == null) {
            return Boolean.FALSE;
        } else {
            return afternoon;
        }
    }

    public void setAfternoon(Boolean afternoon) {
        this.afternoon = afternoon;
    }

    public Boolean getNight() {
        if (night == null) {
            return Boolean.FALSE;
        } else {
            return night;
        }
    }

    public void setNight(Boolean night) {
        this.night = night;
    }

}
