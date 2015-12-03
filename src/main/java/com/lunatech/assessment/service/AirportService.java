package com.lunatech.assessment.service;

import com.google.inject.Inject;
import com.lunatech.assessment.model.Airport;
import com.lunatech.assessment.model.Country;
import com.lunatech.assessment.reader.AirportReader;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.stream.Collectors.*;

/**
 * Created by Victor on 02/12/2015.
 */
public class AirportService extends EntityService<Airport> {

    @Inject private CountryService countryService;

    @Inject
    public AirportService(AirportReader reader) {
        super(reader);
    }

    public List<Airport> findByCountry(Country country) {
        Predicate<Airport> matchesCountryCode = airport -> airport.getCountryCode().equalsIgnoreCase(country.getCode());
        return listAll().stream()
                .filter(matchesCountryCode)
                .collect(toList());
    }

    public Map<String, Long> countByCountryCode() {
        return listAll().stream()
                .collect(groupingBy(Airport::getCountryCode, counting()));
    }

    public Map<Country, Long> countByCountry() {
        return countryService.convertToMapByCountry(countByCountryCode());
    }

}
