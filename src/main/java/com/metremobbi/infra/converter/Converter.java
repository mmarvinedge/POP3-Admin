/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobbi.infra.converter;

import java.util.HashMap;
import java.util.Map;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Renato
 */
@FacesConverter(value = "Converter")
public class Converter extends AbstractConverter {

    private static Map<Object, String> entities = new HashMap<Object, String>();

    public Converter() {
        super(entities);
    }
}