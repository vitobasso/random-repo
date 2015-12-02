package com.lunatech.assessment.service;

import com.lunatech.assessment.model.Country;
import com.lunatech.assessment.reader.CountryReader;

import java.util.function.Predicate;

/**
 * Created by Victor on 02/12/2015.
 */
public class CountryService extends BaseService<Country> {

    public CountryService() {
        super(new CountryReader());
    }

    public Country findMatch(String searchTerm) {
        Predicate<Country> matchesSearch = country -> country.getCode().equalsIgnoreCase(searchTerm)
                                        || country.getName().equalsIgnoreCase(searchTerm);
        return listAll().stream()
                .filter(matchesSearch)
                .findFirst()
                .orElseGet(null);
    }
}
