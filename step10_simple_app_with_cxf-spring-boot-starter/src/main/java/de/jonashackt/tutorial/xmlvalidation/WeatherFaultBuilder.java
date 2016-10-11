package de.jonashackt.tutorial.xmlvalidation;

import de.codecentric.cxf.common.FaultType;
import de.codecentric.cxf.xmlvalidation.CustomFaultBuilder;
import de.codecentric.namespace.weatherservice.exception.WeatherException;
import org.springframework.stereotype.Component;

@Component
public class WeatherFaultBuilder implements CustomFaultBuilder {
	
	private de.codecentric.namespace.weatherservice.exception.ObjectFactory objectFactoryDatatypes = new de.codecentric.namespace.weatherservice.exception.ObjectFactory();
	
	public static final String CUSTOM_ERROR_MSG = "The Weather doesn´t seem to be good";
	
	@Override
	public WeatherException createCustomFaultDetail(String originalFaultMessage, FaultType faultContent) {
		// Build SOAP-Fault detail <datatypes:WeatherException>
		WeatherException weatherException = objectFactoryDatatypes.createWeatherException();		
		weatherException.setBigBusinessErrorCausingMoneyLoss(true);
		weatherException.setBusinessErrorId(faultContent.getId());
		weatherException.setExceptionDetails(originalFaultMessage);
		weatherException.setUuid("ExtremeRandomNumber");
		return weatherException;
	}

    @Override
    public String createCustomFaultMessage(FaultType faultType) {
        return CUSTOM_ERROR_MSG;
    }
}
