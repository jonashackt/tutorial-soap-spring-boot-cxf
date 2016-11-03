package de.jonashackt.tutorial.configuration;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.codecentric.namespace.weatherservice.WeatherService;
import de.jonashackt.tutorial.endpoint.WeatherServiceEndpoint;

@Configuration
public class WebServiceConfiguration {
	
    @Bean
    public ServletRegistrationBean cxfServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/soap-api/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }    
    
    @Bean
    public WeatherService weatherService() {
    	return new WeatherServiceEndpoint();
    }
    
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), weatherService());
        endpoint.publish("/WeatherSoapService_1.0");
        endpoint.setWsdlLocation("Weather1.0.wsdl");
        return endpoint;
    }
}
