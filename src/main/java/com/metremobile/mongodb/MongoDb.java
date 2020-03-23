/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobile.mongodb;

import com.mongodb.DB;

/**
 *
 * @author PICHAU
 */
public class MongoDb {

    // parametros de conexão
    String url = "mongodb+srv://metre:durama357@metredelivery-hewqk.mongodb.net/test?retryWrites=true&w=majority";
    MongoUtils mongo = new MongoUtils(url);

    // inicia conexão
    public Boolean getConnection() {
        Boolean connect;
        try {
            DB db;
            db = mongo.getConection("metre");
            return db.collectionExists("empresa");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
