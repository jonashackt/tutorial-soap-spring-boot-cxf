# tutorial-soap-spring-boot-cxf
Tutorial how to create, test, deploy, monitor SOAP-Webservices using [Spring Boot](http://projects.spring.io/spring-boot/), [Apache CXF](https://cxf.apache.org/) and [JAX-WS](https://de.wikipedia.org/wiki/Java_API_for_XML_Web_Services) 

##### The Steps 1-3: published accompanying the blog-posts: [Spring Boot & Apache CXF – How to SOAP in 2016](https://blog.codecentric.de/en/2016/02/spring-boot-apache-cxf/) (or german version: [Spring Boot & Apache CXF – SOAP ohne XML?](https://blog.codecentric.de/2016/02/spring-boot-apache-cxf/) )

[step1_simple_springboot_app_with_cxf](https://github.com/jonashackt/tutorial-soap-spring-boot-cxf/tree/master/step1_simple_springboot_app_with_cxf)

Shows you, how to set up a simple Spring Boot Application and bootstrap a runnable CXF-Framework within the embedded Tomcat.

[step2_wsdl_2_java_maven](https://github.com/jonashackt/tutorial-soap-spring-boot-cxf/tree/master/step2_wsdl_2_java_maven)

Inherits a completely altered example WebService-Definition as WSDL inspired from the popular http://wsf.cdyne.com/WeatherWS/Weather.asmx?WSDL

Shows, how to generate JAXB-Classes from WSDL with JAX-WS Commons Maven plugin at build time - just run 
```
mvn clean generate-sources
```

[step3_jaxws-endpoint-cxf-spring-boot](https://github.com/jonashackt/tutorial-soap-spring-boot-cxf/tree/master/step3_jaxws-endpoint-cxf-spring-boot)

First running SOAP-Endpoint with SpringBoot, CXF and JAX-WS. For testing use [SoapUI](https://www.soapui.org/) (Testing our Service inside a Unittest will be part of a further Step).

[step3_jaxws-endpoint-cxf-spring-boot-orig-wsdl](https://github.com/jonashackt/tutorial-soap-spring-boot-cxf/tree/master/step3_jaxws-endpoint-cxf-spring-boot-orig-wsdl)

Full-Contract-First with using the generated JAX-WS Service-Class to not wrap WSDL and use original one - includes correct URL and TargetNamespace (recommended)

##### The Steps 4: published accompanying the blog-posts: [Spring Boot & Apache CXF – Testing SOAP Web Services](https://blog.codecentric.de/en/2016/06/spring-boot-apache-cxf-testing-soap-webservices/) (or german version: [
Spring Boot & Apache CXF – SOAP-Webservices testen](https://blog.codecentric.de/2016/06/spring-boot-apache-cxf-soap-webservices-testen/) )

[step4_test](https://github.com/jonashackt/tutorial-soap-spring-boot-cxf/tree/master/step4_test)

Unit-, Integration- and Single-System-Integration-Tests with Spring (Boot) and Apache CXF