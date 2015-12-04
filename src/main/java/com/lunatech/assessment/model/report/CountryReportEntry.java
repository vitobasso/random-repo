package com.lunatech.assessment.model.report;

import com.lunatech.assessment.model.Country;

import java.util.List;
import java.util.Map;

/**
 * Created by Victor on 02/12/2015.
 */
public class CountryReportEntry {

    private Country country;
    private Long airportCount;
    private List<String> surfaceTypes;

    public CountryReportEntry(Map.Entry<Country, Long> entry) {
        this.country = entry.getKey();
        this.airportCount = entry.getValue();
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Long getAirportCount() {
        return airportCount;
    }

    public void setAirportCount(Long airportCount) {
        this.airportCount = airportCount;
    }

    public List<String> getSurfaceTypes() {
        return surfaceTypes;
    }

    public void setSurfaceTypes(List<String> surfaceTypes) {
        this.surfaceTypes = surfaceTypes;
    }
}
