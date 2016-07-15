package de.jonashackt.tutorial.controller;

import org.springframework.stereotype.Component;

import de.codecentric.namespace.weatherservice.general.ForecastRequest;
import de.codecentric.namespace.weatherservice.general.ForecastReturn;
import de.jonashackt.tutorial.transformation.GetCityForecastByZIPOutMapper;

/*
 *  Example-Controller:
 *  This Class would be responsible for Mapping from Request to internal Datamodel (and backwards),
 *  for calling Backend-Services and handling Backend-Exceptions
 *  So it decouples the WSDL-generated Classes from the internal Classes - for when the former changes,
 *  nothing or only the mapping has to be changed
 */ 
@Component
public class WeatherServiceController {
 
    public ForecastReturn getCityForecastByZIP(ForecastRequest forecastRequest) {
	    /*
	     * We leave out inbound transformation, plausibility-checking, logging, backend-calls e.g.
	     * for the moment
	     */
        return GetCityForecastByZIPOutMapper.mapGeneralOutlook2Forecast();
	}
	
	/*
	 * Other Methods would follow here...
	 */
	//public WeatherReturn getCityWeatherByZIP(ForecastRequest forecastRequest) throws BusinessException {}

	//public WeatherInformationReturn getWeatherInformation(String zip) throws BusinessException {}
}
