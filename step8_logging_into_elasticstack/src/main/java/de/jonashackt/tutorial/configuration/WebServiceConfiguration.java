package de.jonashackt.tutorial.configuration;

import de.codecentric.namespace.weatherservice.Weather;
import de.codecentric.namespace.weatherservice.WeatherService;
import de.jonashackt.tutorial.endpoint.WeatherServiceEndpoint;
import de.jonashackt.tutorial.soapmsglogging.LoggingInInterceptorXmlOnly;
import de.jonashackt.tutorial.soapmsglogging.LoggingOutInterceptorXmlOnly;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.interceptor.AbstractLoggingInterceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfiguration {
	
    public static final String BASE_URL = "/soap-api";
    public static final String SERVICE_URL = "/WeatherSoapService_1.0";
    
    @Bean
    public ServletRegistrationBean dispatcherServlet() {
        return new ServletRegistrationBean(new CXFServlet(), BASE_URL + "/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        SpringBus springBus = new SpringBus();
        springBus.getInInterceptors().add(logInInterceptor());
        springBus.getInFaultInterceptors().add(logInInterceptor());
        springBus.getOutInterceptors().add(logOutInterceptor());
        springBus.getOutFaultInterceptors().add(logOutInterceptor());
        return springBus;
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
        return endpoint;
    }
    
    @Bean
    public Weather weather() {
        // Needed for correct ServiceName & WSDLLocation to publish contract first incl. original WSDL
        return new Weather();
    }

    @Bean
    public AbstractLoggingInterceptor logInInterceptor() {
        LoggingInInterceptor logInInterceptor = new LoggingInInterceptorXmlOnly();
        // The In-Messages are pretty without setting it - when setting it Apache CXF throws empty lines into the In-Messages
        return logInInterceptor;
    }

    @Bean
    public AbstractLoggingInterceptor logOutInterceptor() {
        LoggingOutInterceptor logOutInterceptor = new LoggingOutInterceptorXmlOnly();
        logOutInterceptor.setPrettyLogging(true);
        return logOutInterceptor;
    }
}
