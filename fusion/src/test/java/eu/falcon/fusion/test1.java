/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.falcon.fusion;

import eu.falcon.fusion.SensorMeasurement;
import eu.falcon.fusion.SensorMeasurementRepository;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Eleni Fotopoulou <efotopoulou@ubitech.eu>
 */
public class test1 {

    @Autowired
    public SensorMeasurementRepository repository;

    @Ignore
    @Test
    public void test1() {
        try {
            InputStream inputStream = new FileInputStream("/home/eleni/NetBeansProjects/falcon-data-management/fusion/src/main/resources/input.json");

            String serviceURI = "http://192.168.3.15:3030/ds/data";
            //DatasetAccessorFactory factory = null;
            DatasetAccessor accessor;
            accessor = DatasetAccessorFactory.createHTTP(serviceURI);
            Model m = ModelFactory.createDefaultModel();
            String base = "http://test-projects.com/";
            m.read(inputStream, base, "JSON-LD");
            accessor.putModel(m);
            inputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(test1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(test1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Ignore
    @Test
    public void test2() {
        //        repository.deleteAll();
//
        // save a couple of customers
        repository.save(new SensorMeasurement("Sensor1", "type1"));
        repository.save(new SensorMeasurement("Sensor2", "type1"));

        // fetch all customers
        System.out.println("Sensors found with findAll():");
        System.out.println("-------------------------------");
        for (SensorMeasurement customer : this.repository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Sensor found with findBySensorName('Sensor1'):");
        System.out.println("--------------------------------");
        System.out.println(this.repository.findBySensorName("Sensor1"));

        System.out.println("Sensor found with findBySensorType('type1'):");
        System.out.println("--------------------------------");
        this.repository.findBySensorType("type1").forEach((customer) -> {
            System.out.println(customer);
        });
    }

  

}
