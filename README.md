# training-soap-spring-boot-cxf
Tutorial how to create, test, deploy, monitor SOAP-Webservices using Spring Boot and Apache CXF

### The Steps
[step1_simple_springboot_app_with_cxf](https://github.com/jonashackt/tutorial-soap-spring-boot-cxf/tree/master/step1_simple_springboot_app_with_cxf)

Shows you, how to set up a simple Spring Boot Application and bootstrap a runnable CXF-Framework within the embedded Tomcat.

[step2_wsdl_2_java_maven](https://github.com/jonashackt/tutorial-soap-spring-boot-cxf/tree/master/step2_wsdl_2_java_maven)

Inherits a completely altered example-WebService-Definition as WSDL inspired from the popular http://wsf.cdyne.com/WeatherWS/Weather.asmx?WSDL

Shows, how to generate JAXB-Classes from WSDL with JAX-WS Commons Maven plugin at build time - just run 
```
mvn clean generate-sources
```