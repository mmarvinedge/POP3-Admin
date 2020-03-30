/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.util;

import lombok.Getter;
import lombok.Setter;
import okhttp3.MediaType;

/**
 *
 * @author PICHAU
 */
@Getter
@Setter
public class Constantes {
    
    public static final String URL = "http://localhost:4000";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    
}
