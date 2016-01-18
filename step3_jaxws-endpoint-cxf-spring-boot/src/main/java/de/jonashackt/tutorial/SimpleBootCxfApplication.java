package de.jonashackt.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("de.jonashackt.tutorial")
public class SimpleBootCxfApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleBootCxfApplication.class, args);
    }
}
