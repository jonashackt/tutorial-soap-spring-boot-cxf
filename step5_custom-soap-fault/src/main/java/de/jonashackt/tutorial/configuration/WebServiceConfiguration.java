package de.jonashackt.tutorial.configuration;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.codecentric.namespace.weatherservice.Weather;
import de.codecentric.namespace.weatherservice.WeatherService;
import de.jonashackt.tutorial.configuration.customsoapfaults.CustomSoapFaultInterceptor;
import de.jonashackt.tutorial.endpoint.WeatherServiceEndpoint;

@Configuration
public class WebServiceConfiguration {
	
    public static final String BASE_URL = "/soap-api";
    public static final String SERVICE_URL = "/WeatherSoapService_1.0";
    
    @Bean
    public ServletRegistrationBean cxfServlet() {
        return new ServletRegistrationBean(new CXFServlet(), BASE_URL + "/*");
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
        // CXF JAX-WS implementation relies on the correct ServiceName as QName-Object with
        // the name-Attribute´s text <wsdl:service name="Weather"> and the targetNamespace
        // "http://www.codecentric.de/namespace/weatherservice/"
        // Also the WSDLLocation must be set
        endpoint.setServiceName(weather().getServiceName());
        endpoint.setWsdlLocation(weather().getWSDLDocumentLocation().toString());
        endpoint.publish(SERVICE_URL);
        endpoint.getOutFaultInterceptors().add(soapInterceptor());
        return endpoint;
    }
    
    @Bean
    public Weather weather() {
        // Needed for correct ServiceName & WSDLLocation to publish contract first incl. original WSDL
        return new Weather();
    }
    
    @Bean
    public AbstractSoapInterceptor soapInterceptor() {
        return new CustomSoapFaultInterceptor();
    }
}
