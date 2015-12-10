package com.lunatech.assessment;

import com.lunatech.assessment.model.Airport;
import com.lunatech.assessment.model.Country;

/**
 * Created by Victor on 04/12/2015.
 */
public class TestEntityFactory {

    public static Country createCountry(String name, String code) {
        Country country = new Country();
        country.setName(name);
        country.setCode(code);
        return country;
    }

    public static Airport createAirport(String name, String countryCode) {
        Airport airport = new Airport();
        airport.setId(countryCode + "-" + name);
        airport.setName(name);
        airport.setCountryCode(countryCode);
        return airport;
    }

}
