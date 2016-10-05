package de.jonashackt.tutorial;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        SimpleBootCxfSystemTestConfiguration.class,
        SimpleBootCxfApplication.class
})
public class SimpleBootCxfSystemTestApplication {

}
