package com.lunatech.assessment.service;

import com.google.common.base.Joiner;
import com.google.inject.Inject;
import com.lunatech.assessment.model.Airport;
import com.lunatech.assessment.model.Country;
import com.lunatech.assessment.model.report.Report;
import com.lunatech.assessment.model.report.ReportEntry;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.reverse;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

/**
 * Created by Victor on 02/12/2015.
 */
public class ReportService {

    public static final int REPORT_RESULTS_COUNT = 10;

    @Inject private AirportService airportService;
    @Inject private RunwayService runwayService;

    public void printReport() {
        Report report = createReport();
        print(report);
    }

    public Report createReport() {
        Report report = new Report();
        selectCountriesByAirportCount(report);
        setSurfaceTypesForReport(report);
        return report;
    }

    private void selectCountriesByAirportCount(Report report) {
        Map<Country, Long> airportCountByCountry = airportService.countByCountry();
        List<ReportEntry> sortedEntries = getCountsSortedByValue(airportCountByCountry);

        report.setBottomCountries(getBottomResults(sortedEntries));
        report.setTopCoutries(getTopResults(sortedEntries));
    }

    private List<ReportEntry> getBottomResults(List<ReportEntry> sortedEntries) {
        return sortedEntries.subList(0, REPORT_RESULTS_COUNT);
    }

    private List<ReportEntry> getTopResults(List<ReportEntry> sortedEntries) {
        return reverse(sortedEntries).subList(0, REPORT_RESULTS_COUNT);
    }

    private List<ReportEntry> getCountsSortedByValue(Map<Country, Long> airportCountByCountry) {
        Comparator<ReportEntry> byCount = (left, right) -> Long.compare(left.getAirportCount(), right.getAirportCount());
        return airportCountByCountry.entrySet().stream()
                .map(ReportEntry::new)
                .sorted(byCount)
                .collect(toList());
    }

    private void setSurfaceTypesForReport(Report report) {
        Stream<ReportEntry> allEntries = concat(report.getBottomCountries().stream(),
                                                report.getTopCoutries().stream());
        allEntries.forEach(entry ->
                entry.setSurfaceTypes(getSurfaceTypesForCountry(entry.getCountry())));
    }

    private List<String> getSurfaceTypesForCountry(Country country) {
        List<Airport> airports = airportService.findByCountry(country);
        return airports.parallelStream()
                .flatMap(airport -> runwayService.getRunwaySurfaceTypes(airport).stream())
                .distinct()
                .collect(toList());
    }

    public void print(Report report) {
        System.out.println("\nCountries with the most airports:");
        report.getTopCoutries()
                .forEach(this::printEntry);

        System.out.println("\nCountries with the least airports:");
        report.getBottomCountries()
                .forEach(this::printEntry);
    }

    private void printEntry(ReportEntry reportEntry) {
        String countryName = reportEntry.getCountry().getName();
        Long airportCount = reportEntry.getAirportCount();
        String surfacesStr = Joiner.on(", ").join(reportEntry.getSurfaceTypes());
        System.out.printf("%30s %6d   %s\n", countryName, airportCount, surfacesStr);
    }

}
