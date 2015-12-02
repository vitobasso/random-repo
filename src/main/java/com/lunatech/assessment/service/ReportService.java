package com.lunatech.assessment.service;

import com.google.inject.Inject;
import com.lunatech.assessment.model.Country;
import com.lunatech.assessment.model.report.AirportCount;
import com.lunatech.assessment.model.report.Report;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.reverse;
import static java.util.stream.Collectors.toList;

/**
 * Created by Victor on 02/12/2015.
 */
public class ReportService {

    public static final int REPORT_RESULTS = 10;

    @Inject private AirportService airportService;

    public Report createReport() {
        Map<Country, Long> airportCountByCountry = airportService.countByCountry();
        List<AirportCount> sortedEntries = sortByValue(airportCountByCountry);

        Report report = new Report();
        report.setCountriesWithLeastAirports(sortedEntries.subList(0, REPORT_RESULTS));
        report.setCountriesWithMostAirports(reverse(sortedEntries).subList(0, REPORT_RESULTS));

        return report;
    }

    private List<AirportCount> sortByValue(Map<Country, Long> airportCountByCountry) {
        Comparator<AirportCount> byCount = (left, right) -> Long.compare(left.getCount(), right.getCount());
        return airportCountByCountry.entrySet().stream()
                .map(AirportCount::new)
                .sorted(byCount)
                .collect(toList());
    }

}
