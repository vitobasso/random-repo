package com.lunatech.assessment.model.report;

import com.lunatech.assessment.model.Country;

import java.util.List;
import java.util.Map;

/**
 * Created by Victor on 02/12/2015.
 */
public class Report {

    private List<AirportCount> countriesWithMostAirports;
    private List<AirportCount> countriesWithLeastAirports;
    private Map<Country, List<String>> typesOfRunways;

    public List<AirportCount> getCountriesWithMostAirports() {
        return countriesWithMostAirports;
    }

    public void setCountriesWithMostAirports(List<AirportCount> countriesWithMostAirports) {
        this.countriesWithMostAirports = countriesWithMostAirports;
    }

    public List<AirportCount> getCountriesWithLeastAirports() {
        return countriesWithLeastAirports;
    }

    public void setCountriesWithLeastAirports(List<AirportCount> countriesWithLeastAirports) {
        this.countriesWithLeastAirports = countriesWithLeastAirports;
    }

    public Map<Country, List<String>> getTypesOfRunways() {
        return typesOfRunways;
    }

    public void setTypesOfRunways(Map<Country, List<String>> typesOfRunways) {
        this.typesOfRunways = typesOfRunways;
    }


}
