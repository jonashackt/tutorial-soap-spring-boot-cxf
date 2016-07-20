package de.jonashackt.tutorial.soapmsglogging;

import org.apache.cxf.interceptor.LoggingMessage;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.logstash.logback.marker.Markers.append;

public class SoapMsgToMdcExtractionLoggingOutInterceptor extends LoggingOutInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(SoapMsgToMdcExtractionLoggingOutInterceptor.class);
    private static final String SOAP_MESSAGE_OUTBOUND = "soap-message-outbound";

    @Override
    protected void log(java.util.logging.Logger logger, String message) {
        // just do nothing, because we don´t want CXF-Implementation to log,
        // we just want to Push the SOAP-Message to Logback -> Logstash -> Elasticsearch -> Kibana
    }

    @Override
    protected String formatLoggingMessage(LoggingMessage loggingMessage) {
        // Only write the Payload (SOAP-Xml) to Logger
        if (loggingMessage.getPayload().length() > 0) {
            LOG.info(append(SOAP_MESSAGE_OUTBOUND, loggingMessage.getPayload().toString()), "Log Outbound-SoapMessage to Elasticseach");
        }
        // This is just hook into CXF and get the SOAP-Message.
        // The returned String will never be logged somewhere.
        return "";
    }
}
