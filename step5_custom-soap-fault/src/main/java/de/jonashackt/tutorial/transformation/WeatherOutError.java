package de.jonashackt.tutorial.transformation;

import de.codecentric.namespace.weatherservice.exception.WeatherException;
import de.jonashackt.tutorial.common.FaultConst;



public final class WeatherOutError {

	private WeatherOutError() {
	 // private Constructor for Utility-Class
	};
	
	private static final de.codecentric.namespace.weatherservice.exception.ObjectFactory objectFactoryDatatypes = new de.codecentric.namespace.weatherservice.exception.ObjectFactory();
	
	public static WeatherException createWeatherException(FaultConst faultContent, String originalFaultMessage) {
		// Build SOAP-Fault detail <datatypes:WeatherException>
		WeatherException weatherException = objectFactoryDatatypes.createWeatherException();		
		weatherException.setBigBusinessErrorCausingMoneyLoss(true);
		weatherException.setBusinessErrorId(faultContent.getId());
		weatherException.setExceptionDetails(originalFaultMessage);
		weatherException.setUuid("ExtremeRandomNumber");
		return weatherException;
	}

}
