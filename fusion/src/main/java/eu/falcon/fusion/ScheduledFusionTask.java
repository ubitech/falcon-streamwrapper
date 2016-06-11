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
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledFusionTask {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println("The time is now " + dateFormat.format(new Date()));
        InputStream inputStream;
        String jsonldToSaveToMongoAndFuseki = "";

        //doo rest call
        try {

            URL url = new URL("http://localhost:8083");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/ld+json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                jsonldToSaveToMongoAndFuseki += output;
            }

            conn.disconnect();

            inputStream = new ByteArrayInputStream(jsonldToSaveToMongoAndFuseki.getBytes(StandardCharsets.UTF_8));
            //inputStream = new FileInputStream("/home/eleni/NetBeansProjects/falcon-data-management/fusion/src/main/resources/sensorData.json");

            String serviceURI = "http://192.168.3.15:3030/ds/data";
            //DatasetAccessorFactory factory = null;
            DatasetAccessor accessor;
            accessor = DatasetAccessorFactory.createHTTP(serviceURI);
            Model m = ModelFactory.createDefaultModel();
            String base = "http://test-projects.com/";
            m.read(inputStream, base, "JSON-LD");
            //accessor.putModel(m);
            accessor.add(m);
            inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(FusionApplication.class.getName()).log(Level.SEVERE, null, ex);
        }

        DBObject dbObject = (DBObject) JSON.parse(jsonldToSaveToMongoAndFuseki);

        mongoTemplate.insert(dbObject, "sensorMeasurement");

    }

}
