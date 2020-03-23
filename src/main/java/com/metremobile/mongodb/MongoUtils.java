/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metremobile.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Tadeu-PC
 */
public class MongoUtils {

    private final MongoClient mongo;
    private DB db;
//    private final String user;
//    private final String password;

    public MongoUtils(String ip, int port) {
        mongo = new MongoClient(ip, port);
    }

    public MongoUtils(String mongoURI) {
        mongo = new MongoClient(new MongoClientURI(mongoURI));
    }

    public DB getConection(String dbName) {
        db = mongo.getDB(dbName);
        return db;
    }

    public List<String> showDataBases() {
        return mongo.getDatabaseNames();
    }

    public List<String> showCollections() {
        return db.getCollectionNames().stream().collect(Collectors.toList());
    }

    public void insertCollection(String collectionName, Map json) {
        DBCollection table = db.getCollection(collectionName);
        BasicDBObject document = new BasicDBObject(json);
        table.insert(document);
    }

    public void updateCollection(String collectionName, Map oldValue, Map newValue) {
        DBCollection table = db.getCollection(collectionName);
        BasicDBObject oldDocument = new BasicDBObject(oldValue);
        BasicDBObject newDocument = new BasicDBObject(newValue);
        table.update(oldDocument, newDocument);
    }

}
