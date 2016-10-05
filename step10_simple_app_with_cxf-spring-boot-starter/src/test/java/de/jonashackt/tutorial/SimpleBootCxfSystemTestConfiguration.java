package de.jonashackt.tutorial;

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
public class SimpleBootCxfSystemTestConfiguration {

    @Autowired
    private CxfAutoConfiguration cxfAutoConfiguration;

    @Bean
    public WeatherService weatherServiceSystemTestClient() {
        JaxWsProxyFactoryBean jaxWsProxyFactory = new JaxWsProxyFactoryBean();
        jaxWsProxyFactory.setServiceClass(WeatherService.class);
        jaxWsProxyFactory.setAddress("http://localhost:8090" + cxfAutoConfiguration.getBaseUrl() + SimpleBootCxfConfiguration.SERVICE_URL);
        return (WeatherService) jaxWsProxyFactory.create();
    }

    @Bean
    public SoapRawClient soapRawClient() throws BootStarterCxfException {
        return new SoapRawClient(buildUrl(), WeatherService.class);
    }

    private String buildUrl() {
        return "http://localhost:8087" + cxfAutoConfiguration.getBaseUrl() + SimpleBootCxfConfiguration.SERVICE_URL;
    }
}
