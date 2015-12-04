package com.lunatech.assessment.util;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * Created by Victor on 04/12/2015.
 */
public class FuzzyFinder<T> {

    final int FUZZY_SEARCH_TRESHOLD = 5;

    private Supplier<List<T>> listAll;
    private Function<T, String> codeGetter;
    private Function<T, String> nameGetter;

    public FuzzyFinder(Supplier<List<T>> listAll, Function<T, String> codeGetter, Function<T, String> nameGetter) {
        this.listAll = listAll;
        this.codeGetter = codeGetter;
        this.nameGetter = nameGetter;
    }

    public Optional<T> find(String searchTerm) {
        Map<T, Integer> similarityScores = getSimilarityScores(searchTerm);
        Optional<Map.Entry<T, Integer>> bestMatch = getBestScore(similarityScores);
        return getResult(bestMatch);
    }

    private Map<T, Integer> getSimilarityScores(String searchTerm) {
        return listAll.get().stream()
                .collect(toMap(identity(), element -> getSimilarityScore(searchTerm, element)));
    }

    private Integer getSimilarityScore(String searchTerm, T element) {
        String name = nameGetter.apply(element);
        String code = codeGetter.apply(element);
        int nameSimilarity = StringUtils.getFuzzyDistance(searchTerm, name, Locale.ENGLISH);
        int codeSimilarity = searchTerm.equalsIgnoreCase(code) ? Integer.MAX_VALUE : 0;
        return Math.max(nameSimilarity, codeSimilarity);
    }

    private Optional<Map.Entry<T, Integer>> getBestScore(Map<T, Integer> similarityScores) {
        return similarityScores.entrySet().stream()
                .max((left, right) -> left.getValue().compareTo(right.getValue()));
    }

    private Optional<T> getResult(Optional<Map.Entry<T, Integer>> bestMatch) {
        return bestMatch
                .filter(entry -> entry.getValue() >= FUZZY_SEARCH_TRESHOLD)
                .map(Map.Entry::getKey);
    }


}
