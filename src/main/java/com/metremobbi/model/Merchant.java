package com.metremobbi.model;

import lombok.Data;

@Data
public class Merchant {

    private String restaurantID;
    private String cgccpf;

    public Merchant() {
    }

}
