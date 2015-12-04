package com.lunatech.assessment.service;

import com.google.inject.Inject;
import com.lunatech.assessment.model.Country;
import com.lunatech.assessment.reader.CountryReader;
import com.lunatech.assessment.util.FuzzyFinder;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * Created by Victor on 02/12/2015.
 */
public class CountryService extends EntityService<Country> {

    private FuzzyFinder<Country> fuzzyFinder;

    @Inject
    public CountryService(CountryReader reader) {
        super(reader);
        fuzzyFinder = new FuzzyFinder<>(this::listAll, Country::getCode, Country::getName);
    }

    public Optional<Country> find(String searchTerm) {
        Predicate<Country> matchesSearch = country -> country.getCode().equalsIgnoreCase(searchTerm)
                                        || country.getName().equalsIgnoreCase(searchTerm);
        return listAll().stream()
                .filter(matchesSearch)
                .findFirst();
    }

    public Optional<Country> findFuzzy(String searchTerm) {
        return fuzzyFinder.find(searchTerm);
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
