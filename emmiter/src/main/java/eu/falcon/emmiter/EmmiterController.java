/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.falcon.emmiter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Eleni Fotopoulou <efotopoulou@ubitech.eu>
 */
@RestController
public class EmmiterController {

    @RequestMapping(method = RequestMethod.GET, produces = "application/ld+json")
    public String helloEmmiter() {
        JSONObject a = null;
        try {

            InputStream inputStream = this.getClass().getResourceAsStream("/sensorData.json");

            String myString = IOUtils.toString(inputStream, "UTF-8");
            a = new JSONObject(myString);
            System.out.println("a" + a.toString());

        } catch (IOException ex) {
            Logger.getLogger(EmmiterController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return a.toString();
    }

}
