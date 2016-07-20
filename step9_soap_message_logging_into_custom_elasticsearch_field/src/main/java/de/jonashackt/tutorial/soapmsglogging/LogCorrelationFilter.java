package de.jonashackt.tutorial.soapmsglogging;

import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

public class LogCorrelationFilter implements Filter {

    private static final String SERVICE_CALL_ID_KEY = "service-call-id";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Put an unique-Logging-Id to the logback Mapped Diagnostic Context to correlate
        // against one Customer-Request, see http://logback.qos.ch/manual/mdc.html
        MDC.put(SERVICE_CALL_ID_KEY, UUID.randomUUID().toString());
        try {
            chain.doFilter(request, response);
        } finally {
            // finally remove unique-Logging-Id, so that it could´nt be accidentally
            // reused for another Consumer-Request
            MDC.remove(SERVICE_CALL_ID_KEY);
        }       
    }

    @Override
    public void destroy() {}

}
