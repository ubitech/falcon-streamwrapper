/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.falcon.fusion;

/**
 *
 * @author Eleni Fotopoulou <efotopoulou@ubitech.eu>
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
 

/**
 * Example connection to Fuseki. For this to work, you need to start a local
 * Fuseki server like this: ./fuseki-server --update --mem /ds
 */
public class FusekiJSONLD {
    /** A template for creating a nice SPARUL query */
    private static final String UPDATE_TEMPLATE = 
            "PREFIX dc: <http://purl.org/dc/elements/1.1/>"
            + "INSERT DATA"
            + "{ <http://example/%s>    dc:title    \"A new book\" ;"
            + "                         dc:creator  \"A.N.Other\" ." + "}   ";
 
    public static void main(String[] args) {
        
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("/home/eleni/NetBeansProjects/falcon-data-management/fusion/src/main/resources/input.json");
            //Add a new book to the collection
            String id = UUID.randomUUID().toString();
            System.out.println(String.format("Adding %s", id));
            //UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.create(String.format(UPDATE_TEMPLATE, id)),"http://192.168.3.15:3030/ds/update");
            UpdateRequest a = UpdateFactory.read(inputStream);
            UpdateProcessor upp = UpdateExecutionFactory.createRemote(UpdateFactory.read(inputStream),"http://192.168.3.15:3030/ds/update");
            //RDFDataMgr.write(OutputStream, Model, RDFFormat.JSONLD) ;
            
            upp.execute();
            //Query the collection, dump output
            QueryExecution qe = QueryExecutionFactory.sparqlService("http://192.168.3.15:3030/ds/query", "SELECT * WHERE {?x <http://purl.org/dc/elements/1.1/title> ?y}");
            ResultSet results = qe.execSelect();
            ResultSetFormatter.out(System.out, results);
            qe.close();
            inputStream.close();
            
            
            
//          OutputStream os = new ByteArrayOutputStream();
//model.write(os, "JSON-LD");
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FusekiJSONLD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FusekiJSONLD.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
 
}