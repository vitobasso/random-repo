package com.lunatech.assessment.service;

import com.google.inject.Inject;
import com.lunatech.assessment.model.Country;
import com.lunatech.assessment.reader.CountryReader;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * Created by Victor on 02/12/2015.
 */
public class CountryService extends EntityService<Country> {



    @Inject
    public CountryService(CountryReader reader) {
        super(reader);
    }

    public Optional<Country> find(String searchTerm) {
        Predicate<Country> matchesSearch = country -> country.getCode().equalsIgnoreCase(searchTerm)
                                        || country.getName().equalsIgnoreCase(searchTerm);
        return listAll().stream()
                .filter(matchesSearch)
                .findFirst();
    }

    public Optional<Country> findFuzzy(String searchTerm) {
        final int FUZZY_SEARCH_TRESHOLD = 5;
        Map<Country, Integer> similarityScores = listAll().stream()
                .collect(toMap(identity(), country -> getSimilarityScore(searchTerm, country)));
        Optional<Map.Entry<Country, Integer>> bestMatch = similarityScores.entrySet().stream()
                .max((left, right) -> left.getValue().compareTo(right.getValue()));
        return bestMatch
                .filter(entry -> entry.getValue() >= FUZZY_SEARCH_TRESHOLD)
                .map(Map.Entry::getKey);
    }

    private Integer getSimilarityScore(String searchTerm, Country country) {
        int nameSimilarity = StringUtils.getFuzzyDistance(searchTerm, country.getName(), Locale.ENGLISH);
        int codeSimilarity = searchTerm.equalsIgnoreCase(country.getCode()) ? Integer.MAX_VALUE : 0;
        return Math.max(nameSimilarity, codeSimilarity);
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
