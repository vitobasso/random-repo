package com.lunatech.assessment.service;

import com.google.inject.Inject;
import com.lunatech.assessment.model.Country;
import com.lunatech.assessment.reader.CountryReader;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * Created by Victor on 02/12/2015.
 */
public class CountryService extends EntityService<Country> {

    @Inject
    public CountryService(CountryReader reader) {
        super(reader);
    }

    public Country findMatch(String searchTerm) {
        Predicate<Country> matchesSearch = country -> country.getCode().equalsIgnoreCase(searchTerm)
                                        || country.getName().equalsIgnoreCase(searchTerm);
        return listAll().stream()
                .filter(matchesSearch)
                .findFirst()
                .orElse(null);
    }

    public <T> Map<Country, T> convertToMapByCountry(Map<String, T> mapByCode) {
        Map<String, Country> countriesByCode = getCountriesByCode();
        return mapByCode.entrySet().stream()
                .collect(toMap(
                        entry -> countriesByCode.get(entry.getKey()),
                        Map.Entry::getValue
                ));
    }

    private Map<String, Country> getCountriesByCode() {
        return listAll().stream()
                .collect(Collectors.toMap(Country::getCode, x -> x));
    }

}
