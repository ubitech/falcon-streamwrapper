package eu.falcon.emmiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({
    "eu.falcon.emmiter"
})

@EnableAutoConfiguration
public class EmmiterApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EmmiterApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(EmmiterApplication.class, args);

    }

}
