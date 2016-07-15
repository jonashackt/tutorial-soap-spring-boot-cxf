package de.jonashackt.tutorial.soapmsglogging;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingMessage;

public class LoggingInInterceptorXmlOnly extends LoggingInInterceptor {

	@Override
    protected String formatLoggingMessage(LoggingMessage loggingMessage) {
        StringBuilder buffer = new StringBuilder();
        // Only write the Payload (SOAP-Xml) to Logger
        if (loggingMessage.getPayload().length() > 0) {
            buffer.append("Inbound Message:\n");
            buffer.append(loggingMessage.getPayload());
        }        
        return buffer.toString();
    }

}
