package com.lunatech.assessment.service.entity;

import com.google.inject.Inject;
import com.lunatech.assessment.model.Country;
import com.lunatech.assessment.reader.CountryReader;
import com.lunatech.assessment.util.FuzzyFinder;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

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

    @Override
    protected String getId(Country entity) {
        return entity.getCode();
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
        Map<String, Country> countriesByCode = getMapById();
        return mapByCode.entrySet().stream()
                .collect(toMap(
                        entry -> countriesByCode.get(entry.getKey()),
                        Map.Entry::getValue
                ));
    }

}
