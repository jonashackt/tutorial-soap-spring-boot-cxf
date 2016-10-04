package de.jonashackt.tutorial.endpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WebServiceSystemTestConfiguration.class)
public class SimpleBootCxfSystemTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleBootCxfSystemTestApplication.class, args);
    }
}
