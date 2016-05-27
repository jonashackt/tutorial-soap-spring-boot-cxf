package de.jonashackt.tutorial;

import de.codecentric.namespace.weatherservice.datatypes.ProductName;
import de.codecentric.namespace.weatherservice.general.ForecastCustomer;
import de.codecentric.namespace.weatherservice.general.ForecastRequest;

public class TestHelper {

    public static ForecastRequest generateDummyRequest() {
        ForecastRequest forecastRequest = new ForecastRequest();
        forecastRequest.setZIP("99425");
        forecastRequest.setFlagcolor("blackblue");
        forecastRequest.setProductName(ProductName.FORECAST_BASIC);
        ForecastCustomer customer = new ForecastCustomer();
        customer.setAge(67);
        customer.setContribution(500);
        customer.setMethodOfPayment("Bitcoin");
        forecastRequest.setForecastCustomer(customer);
        return forecastRequest;
    }
}
