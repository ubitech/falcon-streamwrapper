package eu.falcon.emmiter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Eleni Fotopoulou <efotopoulou@ubitech.eu>
 */
@RestController
public class EmmiterController {

    private static final Logger logger = Logger.getLogger(EmmiterController.class.getName());

    @Autowired
    private Environment environment;

    @Value("${sensor.deviceid}")
    private String deviceid;

    @RequestMapping(method = RequestMethod.GET, produces = "application/ld+json")
    public String helloEmitter() {
        logger.log(Level.INFO, "deviceid: " + deviceid);
        JSONObject a = null;
        try {

            String myString = "";
            //get from static file
//            InputStream inputStream = this.getClass().getResourceAsStream("/sensorData.json");
//            myString= IOUtils.toString(inputStream, "UTF-8");

            myString = generateMeasurement(deviceid);
            a = new JSONObject(myString);
            logger.log(Level.INFO, "a" + a.toString());

        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        return a.toString();
    }


    private static String generateMeasurement(String devid) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
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
                + "    \"timestamp\": \"http://www.w3.org/2006/timestamp\"\n"
                + "\n"
                + "  },\n"
                + "  \"@id\": \"http://eu.falcon.net/" + id + "\",\n"
                + "  \"@type\": \"SensorDevice\",\n"
                + "  \"value\": \"" + value + "\",\n"
                + "  \"temp\": \"" + temp + "\",\n"
                + "  \"DeviceIdentifier\": \"" + devid + "\",\n"
                + "  \"timestamp\": \"" + timeStamp + "\",\n"
                + "  \"UnitOfMeasure\": \"milli_watt_hour\"\n"
                + " \n"
                + "}";

        String str = "{\n"
                + "	\"@type\": \"SensorDevice\",\n"
                + "	\"@id\": \"http://eu.falcon.net/" + id + "\",\n"
                + "	\"@context\": {\n"
                + "		\"UnitOfMeasure\": \"https://w3id.org/saref#UnitOfMeasure\",\n"
                + "		\"SensorDevice\": \"https://w3id.org/saref#SensorDevice\",\n"
                + "		\"DeviceCategory\": \"https://w3id.org/saref#DeviceCategory\",\n"
                + "		\"DeviceIdentifier\": \"https://w3id.org/saref#DeviceIdentifier\",\n"
                + "		\"measurement\": \"https://w3id.org/saref#Measurement\",\n"
                + "		\"value\": \"https://w3id.org/saref#MeasurementValue\",\n"
                + "		\"consumption\": \"https://w3id.org/saref#AverageEnergy\",\n"
                + "		\"timestamp\": \"http://www.w3.org/2006/timestamp\",\n"
                + "		\"temp\": \"https://w3id.org/saref#AverageTemperature\"\n"
                + "	},\n"
                + "	\"measurement\": [{\n"
                + "		\"@id\": \"http://eu.falcon.net/c/" + id + "\",\n"
                + "		\"@type\": \"consumption\",\n"
                + "		\"value\": \"" + value + "\",\n"
                + "		\"UnitOfMeasure\": \"milli_watt_hour\",\n"
                + "		\"DeviceIdentifier\": \"Arduino-111\",\n"
                + "		\"timestamp\": \"" + timeStamp + "\"\n"
                + "	}, {\n"
                + "		\"@id\": \"http://eu.falcon.net/t/" + id + "\",\n"
                + "		\"@type\": \"temp\",\n"
                + "		\"value\": \"" + temp + "\",\n"
                + "		\"UnitOfMeasure\": \"celcius\",\n"
                + "		\"DeviceIdentifier\": \"Arduino-111\",\n"
                + "		\"timestamp\": \"" + timeStamp + "\"\n"
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
