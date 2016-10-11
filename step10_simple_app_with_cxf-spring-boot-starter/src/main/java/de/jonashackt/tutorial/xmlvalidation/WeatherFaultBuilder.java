package de.jonashackt.tutorial.xmlvalidation;

import de.codecentric.cxf.common.FaultType;
import de.codecentric.cxf.xmlvalidation.CustomFaultBuilder;
import de.codecentric.namespace.weatherservice.exception.WeatherException;
import de.jonashackt.tutorial.common.CustomIds;
import org.springframework.stereotype.Component;

@Component
public class WeatherFaultBuilder implements CustomFaultBuilder {

	private de.codecentric.namespace.weatherservice.exception.ObjectFactory objectFactoryDatatypes = new de.codecentric.namespace.weatherservice.exception.ObjectFactory();

	@Override
	public String createCustomFaultMessage(FaultType faultType) {
		if(FaultType.SCHEME_VALIDATION_ERROR.equals(faultType)) {
			return CustomIds.NON_XML_COMPLIANT.getMessage();
		}
		else if(FaultType.SYNTACTICALLY_INCORRECT_XML_ERROR.equals(faultType)) {
			return CustomIds.COMPLETE_USELESS_XML.getMessage();
		}
		else {
			return CustomIds.SOMETHING_ELSE_WENT_TERRIBLY_WRONG.getMessage();
		}
	}

	@Override
	public WeatherException createCustomFaultDetail(String originalFaultMessage, FaultType faultType) {
		// Build SOAP-Fault detail <datatypes:WeatherException>
		WeatherException weatherException = objectFactoryDatatypes.createWeatherException();
		weatherException.setBigBusinessErrorCausingMoneyLoss(true);
		setIdBasedUponFaultContent(faultType, weatherException);
		weatherException.setExceptionDetails(originalFaultMessage);
		weatherException.setUuid("ExtremeRandomNumber");
		return weatherException;
	}

	private void setIdBasedUponFaultContent(FaultType faultType, WeatherException weatherException) {
		if(FaultType.SCHEME_VALIDATION_ERROR.equals(faultType)) {
			weatherException.setBusinessErrorId(CustomIds.NON_XML_COMPLIANT.getId());
		}
		else if(FaultType.SYNTACTICALLY_INCORRECT_XML_ERROR.equals(faultType)) {
			weatherException.setBusinessErrorId(CustomIds.COMPLETE_USELESS_XML.getId());
		}
		else {
			weatherException.setBusinessErrorId(CustomIds.SOMETHING_ELSE_WENT_TERRIBLY_WRONG.getId());
		}
	}
}
