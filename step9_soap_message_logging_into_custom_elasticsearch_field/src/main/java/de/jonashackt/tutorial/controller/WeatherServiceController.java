package de.jonashackt.tutorial.controller;

import de.codecentric.namespace.weatherservice.general.ForecastRequest;
import de.codecentric.namespace.weatherservice.general.ForecastReturn;
import de.jonashackt.tutorial.transformation.GetCityForecastByZIPOutMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
 *  Example-Controller:
 *  This Class would be responsible for Mapping from Request to internal Datamodel (and backwards),
 *  for calling Backend-Services and handling Backend-Exceptions
 *  So it decouples the WSDL-generated Classes from the internal Classes - for when the former changes,
 *  nothing or only the mapping has to be changed
 */ 
@Component
public class WeatherServiceController {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherServiceController.class);

    public ForecastReturn getCityForecastByZIP(ForecastRequest forecastRequest) {
        /*
	     * We leave out inbound transformation, plausibility-checking, logging, backend-calls e.g.
	     * for the moment
	     *
	     * Just some Log-Statements here :)
	     */
        LOG.info("Starting inbound transformation into internal datamodel");

        LOG.info("Checking plausibility of data");

        LOG.info("Calling Backend No. 1");

        LOG.info("Calling Backend No. 2");

        LOG.info("Starting outbound transformation into external datamodel");

        return GetCityForecastByZIPOutMapper.mapGeneralOutlook2Forecast();
	}
	
	/*
	 * Other Methods would follow here...
	 */
	//public WeatherReturn getCityWeatherByZIP(ForecastRequest forecastRequest) throws BusinessException {}

	//public WeatherInformationReturn getWeatherInformation(String zip) throws BusinessException {}
}
