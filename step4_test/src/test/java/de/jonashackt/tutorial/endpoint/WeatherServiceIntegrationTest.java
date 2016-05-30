package de.jonashackt.tutorial.endpoint;

import static de.jonashackt.tutorial.utils.TestHelper.generateDummyRequest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.codecentric.namespace.weatherservice.WeatherException;
import de.codecentric.namespace.weatherservice.WeatherService;
import de.codecentric.namespace.weatherservice.general.ForecastRequest;
import de.codecentric.namespace.weatherservice.general.ForecastReturn;
import de.jonashackt.tutorial.WebServiceIntegrationTestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=WebServiceIntegrationTestConfiguration.class)
public class WeatherServiceIntegrationTest {

    @Autowired
    private WeatherService weatherServiceIntegrationTestClient;
    
    @Test
    public void getCityForecastByZIP() throws WeatherException {
        // Given
        ForecastRequest forecastRequest = generateDummyRequest();
        
        // When
        ForecastReturn forecastReturn = weatherServiceIntegrationTestClient.getCityForecastByZIP(forecastRequest);
        
        // Then
        assertNotNull(forecastReturn);
        assertEquals(true, forecastReturn.isSuccess());
        assertEquals("Weimar", forecastReturn.getCity());
        assertEquals("22%", forecastReturn.getForecastResult().getForecast().get(0).getProbabilityOfPrecipiation().getDaytime());
    }
}
