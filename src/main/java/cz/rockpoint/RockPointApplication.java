package cz.rockpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

// Class that runs the application
@SpringBootApplication
public class RockPointApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(RockPointApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(RockPointApplication.class);
    }
}
