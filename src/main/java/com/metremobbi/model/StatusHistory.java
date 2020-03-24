/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.model;

import com.metremobbi.model.Order.Status;

/**
 *
 * @author JOAO PAULO
 */
public class StatusHistory {
    
    private String updatedBy;
    private String updatedAt;
    private String statusInt;
    private Status status;
    private String stateInt;

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStatusInt() {
        return statusInt;
    }

    public void setStatusInt(String statusInt) {
        this.statusInt = statusInt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getStateInt() {
        return stateInt;
    }

    public void setStateInt(String stateInt) {
        this.stateInt = stateInt;
    }
    
    
}
