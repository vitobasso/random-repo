package com.lunatech.assessment.service;

import com.lunatech.assessment.model.Airport;
import com.lunatech.assessment.model.Country;
import com.lunatech.assessment.reader.AirportReader;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Victor on 02/12/2015.
 */
public class AirportService extends BaseService<Airport> {

    public AirportService() {
        super(new AirportReader());
    }

    public List<Airport> findByCountry(Country country) {
        Predicate<Airport> matchesCountryCode = airport -> airport.getCountryCode().equalsIgnoreCase(country.getCode());
        return listAll().stream()
                .filter(matchesCountryCode)
                .collect(Collectors.toList());
    }

}
