/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.falcon.fusion;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 *
 * @author Eleni Fotopoulou <efotopoulou@ubitech.eu>
 */
public class TestTimeSeries {

    @Test
    public void testtimeseries() {

        BufferedReader br1 = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br1 = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/dates.csv")));
            while ((line = br1.readLine()) != null) {

                // use comma as separator
                String[] date = line.split(cvsSplitBy);

                System.out.println(date[0]);
                InputStream inputStream;
                String jsonldToSaveToMongoAndFuseki = generateMeasurement("F284211", date[0]);
                System.out.println("jsonldToSaveToMongoAndFuseki" + jsonldToSaveToMongoAndFuseki);

                inputStream = new ByteArrayInputStream(jsonldToSaveToMongoAndFuseki.getBytes(StandardCharsets.UTF_8));
                //inputStream = new FileInputStream("/home/eleni/NetBeansProjects/falcon-data-management/fusion/src/main/resources/sensorData.json");

                String serviceURI = "http://localhost:3030/datetimeseries/data";
                //DatasetAccessorFactory factory = null;
                DatasetAccessor accessor;
                accessor = DatasetAccessorFactory.createHTTP(serviceURI);
                Model m = ModelFactory.createDefaultModel();
                String base = "http://test-projects.com/";
                m.read(inputStream, base, "JSON-LD");
                //accessor.putModel(m);
                accessor.add(m);
                inputStream.close();

                DBObject dbObject = (DBObject) JSON.parse(jsonldToSaveToMongoAndFuseki);


            }
            br1.close();

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

    }

    private static String generateMeasurement(String devid, String date) {
        String str = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");



//            Date date1 = formatter.parse(date);
//
//            String timeStamp = formatter.format(date1);
//            System.out.println("timeStamp"+timeStamp);
            String value = "" + randInt(1000, 3500);
            String temp = "" + randInt(17, 40);
            String id = ("" + (new Random()).nextDouble()).substring(2);

            String strall = "{\n"
                    + "  \"@context\": {\n"
                    + "    \"value\": \"https://w3id.org/saref#AverageEnergy\",\n"
                    + "    \"temp\": \"https://w3id.org/saref#AverageTemperature\",\n"
                    + "    \"SensorDevice\": \"https://w3id.org/saref#SensorDevice\",\n"
                    + "    \"UnitOfMeasure\": \"https://w3id.org/saref#UnitOfMeasure\",\n"
                    + "    \"DeviceCategory\": \"https://w3id.org/saref#DeviceCategory\",\n"
                    + "    \"ical\": \"http://www.w3.org/2002/12/cal/ical#\",\n"
                    + "        \"xsd\": \"http://www.w3.org/2001/XMLSchema#\",\n"
                    + "        \"ical:dtstart\": {\n"
                    + "          \"@type\": \"xsd:dateTime\"\n"
                    + "    }"
                    + "  },\n"
                    + "  \"@id\": \"http://eu.falcon.net/" + id + "\",\n"
                    + "  \"@type\": \"SensorDevice\",\n"
                    + "  \"value\": \"" + value + "\",\n"
                    + "  \"temp\": \"" + temp + "\",\n"
                    + "  \"DeviceIdentifier\": \"" + devid + "\",\n"
                    + "  \"ical:dtstart\": \"" + date + "\",\n"
                    + "  \"UnitOfMeasure\": \"milli_watt_hour\"\n"
                    + " \n"
                    + "}";

            str = "{\n"
                    + "	\"@type\": \"SensorDevice\",\n"
                    + "	\"@id\": \"http://eu.falcon.net/" + id + "\",\n"
                    + "	\"@context\": {\n"
                    + "		\"temp\": \"https://w3id.org/saref#AverageTemperature\",\n"
                    + "		\"UnitOfMeasure\": \"https://w3id.org/saref#UnitOfMeasure\",\n"
                    + "		\"SensorDevice\": \"https://w3id.org/saref#SensorDevice\",\n"
                    + "		\"DeviceCategory\": \"https://w3id.org/saref#DeviceCategory\",\n"
                    + "		\"consumption\": \"https://w3id.org/saref#AverageEnergy\",\n"
                    + "		\"value\": \"https://w3id.org/saref#MeasurementValue\",\n"
                    + "		\"measurement\": \"https://w3id.org/saref#Measurement\",\n"
                    + "		\"DeviceIdentifier\": \"https://w3id.org/saref#DeviceIdentifier\",\n"
                    + "		\"ical\": \"http://www.w3.org/2002/12/cal/ical#\",\n"
                    + "		\"xsd\": \"http://www.w3.org/2001/XMLSchema#\",\n"
                    + "		\"ical:dtstart\": {\n"
                    + "			\"@type\": \"xsd:dateTime\"\n"
                    + "		}\n"
                    + "	},\n"
                    + "	\"measurement\": [{\n"
                    + "		\"UnitOfMeasure\": \"milli_watt_hour\",\n"
                    + "		\"@type\": \"consumption\",\n"
                    + "		\"@id\": \"http://eu.falcon.net/c/" + id + "\",\n"
                    + "		\"value\": \"" + value + "\",\n"
                    + "		\"DeviceIdentifier\": \"Arduino-111\",\n"
                    + "		\"ical:dtstart\": \"" + date + "\"\n"
                    + "	}, {\n"
                    + "		\"UnitOfMeasure\": \"celcius\",\n"
                    + "		\"@type\": \"temp\",\n"
                    + "		\"@id\": \"http://eu.falcon.net/t/" + id + "\",\n"
                    + "		\"value\": \"" + temp + "\",\n"
                    + "		\"DeviceIdentifier\": \"Arduino-111\",\n"
                    + "		\"ical:dtstart\": \"" + date + "\"\n"
                    + "	}]\n"
                    + "}";

        
        return str;
    }

    public static int randInt(int min, int max) {
        // Usually this can be a field rather than a method variable
        Random rand = new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

}
