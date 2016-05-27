package de.jonashackt.tutorial;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.codecentric.namespace.weatherservice.WeatherService;
import de.jonashackt.tutorial.configuration.WebServiceConfiguration;

@Configuration
public class WebServiceIntegrationTestConfiguration {
    
    @Bean
    public WeatherService weatherServiceIntegrationTestClient() {
        JaxWsProxyFactoryBean jaxWsProxyFactory = new JaxWsProxyFactoryBean();
        jaxWsProxyFactory.setServiceClass(WeatherService.class);
        jaxWsProxyFactory.setAddress("http://localhost:8080" + WebServiceConfiguration.BASE_URL + WebServiceConfiguration.SERVICE_URL);
        return (WeatherService) jaxWsProxyFactory.create();
    }
}
