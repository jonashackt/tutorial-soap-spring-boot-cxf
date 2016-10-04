package de.jonashackt.tutorial.xmlvalidation;

import de.codecentric.cxf.common.BootStarterCxfException;
import de.codecentric.cxf.configuration.CxfAutoConfiguration;
import de.codecentric.cxf.soaprawclient.SoapRawClient;
import de.codecentric.namespace.weatherservice.WeatherService;
import de.jonashackt.tutorial.configuration.SimpleBootCxfConfiguration;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServiceXmlErrorSystemTestConfiguration {

    @Bean
    public SoapRawClient soapRawClient() throws BootStarterCxfException {
        return new SoapRawClient(buildUrl(), WeatherService.class);
    }
    
    private String buildUrl() {
        return "http://localhost:8087" + cxfAutoConfiguration.getBaseUrl() + SimpleBootCxfConfiguration.SERVICE_URL;
    }
    
    @Autowired
    private CxfAutoConfiguration cxfAutoConfiguration;
}
