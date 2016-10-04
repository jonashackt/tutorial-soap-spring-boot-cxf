package de.jonashackt.tutorial.xmlvalidation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WebServiceXmlErrorSystemTestConfiguration.class)
public class XmlErrorSystemTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(XmlErrorSystemTestApplication.class, args);
    }
}
