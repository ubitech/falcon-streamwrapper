package eu.falcon.fusion;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan({
    //Contains all the security configuration regarding the Arcadia Framework 
    "eu.falcon.fusion"}
)

@EnableMongoRepositories("eu.falcon.fusion")
@EnableAutoConfiguration
@EnableScheduling
public class FusionApplication {

    @Autowired
    public SensorMeasurementRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public boolean lala() {
       

        return true;
    }

    public static void main(String[] args) {
        SpringApplication.run(FusionApplication.class, args);

    }

}
