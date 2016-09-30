package de.jonashackt.tutorial.configuration;

import de.codecentric.namespace.weatherservice.Weather;
import de.codecentric.namespace.weatherservice.WeatherService;
import de.jonashackt.tutorial.endpoint.WeatherServiceEndpoint;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfiguration {

    @Autowired
    private SpringBus springBus;

    @Bean
    public WeatherService weatherService() {
        return new WeatherServiceEndpoint();
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus, weatherService());
        endpoint.setServiceName(weatherClient().getServiceName());
        endpoint.setWsdlLocation(weatherClient().getWSDLDocumentLocation().toString());
        endpoint.publish("/WeatherSoapService_1.0");
        return endpoint;
    }

    @Bean
    public Weather weatherClient() {
        return new Weather();
    }
}
