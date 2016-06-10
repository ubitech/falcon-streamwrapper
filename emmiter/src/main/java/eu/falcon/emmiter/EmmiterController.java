package eu.falcon.emmiter;

import java.io.IOException;
import java.io.InputStream;
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
    public String helloEmmiter() {
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
        String value = ""+randInt(1000,3500);
        String id = (""+(new Random()).nextDouble()).substring(2);
        
        String str = "{\n"
                + "  \"@context\": {\n"
                + "    \"value\": \"https://w3id.org/saref#AverageEnergy\",\n"
                + "    \"SensorDevice\": \"https://w3id.org/saref#SensorDevice\",\n"
                + "    \"UnitOfMeasure\": \"https://w3id.org/saref#UnitOfMeasure\",\n"
                + "    \"DeviceCategory\": \"https://w3id.org/saref#DeviceCategory\",\n"
                + "    \"timestamp\": \"http://www.w3.org/2006/timestamp\"\n"
                + "\n"
                + "  },\n"
                + "  \"@id\": \"http://eu.falcon.net/"+id+"\",\n"
                + "  \"@type\": \"SensorDevice\",\n"
                + "  \"value\": \""+value+"\",\n"
                + "  \"DeviceIdentifier\": \"" + devid + "\",\n"
                + "  \"timestamp\": \"" + timeStamp + "\",\n"
                + "  \"UnitOfMeasure\": \"milli_watt_hour\"\n"
                + " \n"
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
