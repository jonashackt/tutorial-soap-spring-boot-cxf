package de.jonashackt.tutorial;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import de.codecentric.namespace.weatherservice.WeatherService;
import de.jonashackt.tutorial.configuration.ApplicationConfiguration;
import de.jonashackt.tutorial.endpoint.WeatherServiceEndpoint;

@Configuration
@Import(ApplicationConfiguration.class)
public class ApplicationTestConfiguration {

    @Bean
    public WeatherService weatherService() {
        return new WeatherServiceEndpoint();
    }
}
