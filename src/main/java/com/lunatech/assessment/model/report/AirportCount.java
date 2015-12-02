package com.lunatech.assessment.model.report;

import com.lunatech.assessment.model.Country;

import java.util.Map;

/**
 * Created by Victor on 02/12/2015.
 */
public class AirportCount {

    private Country country;
    private Long count;

    public AirportCount() {
    }

    public AirportCount(Country country, Long count) {
        this.country = country;
        this.count = count;
    }

    public AirportCount(Map.Entry<Country, Long> entry) {
        this.country = entry.getKey();
        this.count = entry.getValue();
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
