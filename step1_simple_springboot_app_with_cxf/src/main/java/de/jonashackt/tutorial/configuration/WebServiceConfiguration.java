package de.jonashackt.tutorial.configuration;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServiceConfiguration {
	
	@Bean
    public ServletRegistrationBean dispatcherServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/soap-api/*");
    }
    
    @Bean(name=Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {    	
    	return new SpringBus();
    }

}
