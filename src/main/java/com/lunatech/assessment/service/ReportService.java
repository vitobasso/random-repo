package com.lunatech.assessment.service;

import com.google.common.base.Joiner;
import com.google.inject.Inject;
import com.lunatech.assessment.model.Airport;
import com.lunatech.assessment.model.Country;
import com.lunatech.assessment.model.report.CountryReportEntry;
import com.lunatech.assessment.model.report.LatitudeReportEntry;
import com.lunatech.assessment.model.report.Report;
import com.lunatech.assessment.service.entity.AirportService;
import com.lunatech.assessment.service.entity.RunwayService;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.lunatech.assessment.util.Lang.getFirstElements;
import static com.lunatech.assessment.util.Lang.getLastElements;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

/**
 * Created by Victor on 02/12/2015.
 */
public class ReportService {

    public static final int ENTRIES_COUNT = 10;

    @Inject private AirportService airportService;
    @Inject private RunwayService runwayService;

    public void report() {
        Report report = createReport();
        printReport(report);
    }

    private Report createReport() {
        Report report = new Report();

        selectCountriesByAirportCount(report);
        setSurfaceTypesForCountries(report);

        report.setCommonLatitudes(getLatitudeEntries());

        return report;
    }

    private void selectCountriesByAirportCount(Report report) {
        List<CountryReportEntry> countryEntries = getCountryEntries();
        report.setBottomCountries(getFirstElements(countryEntries, ENTRIES_COUNT));
        report.setTopCoutries(getLastElements(countryEntries, ENTRIES_COUNT));
    }

    private List<CountryReportEntry> getCountryEntries() {
        Map<Country, Long> airportCountByCountry = airportService.countByCountry();
        return airportCountByCountry.entrySet().stream()
                .map(CountryReportEntry::new)
                .sorted(comparing(CountryReportEntry::getAirportCount))
                .collect(toList());
    }

    private void setSurfaceTypesForCountries(Report report) {
        Stream<CountryReportEntry> allEntries = concat(report.getBottomCountries().stream(),
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

    private List<LatitudeReportEntry> getLatitudeEntries() {
        Map<String, Long> latitudeCounts = runwayService.countByLatitudeCircle();
        return latitudeCounts.entrySet().stream()
                .map(LatitudeReportEntry::new)
                .sorted(reverseOrder(comparing(LatitudeReportEntry::getCount)))
                .limit(ENTRIES_COUNT)
                .collect(toList());
    }

    private void printReport(Report report) {
        System.out.println("\nCountries with the most airports:");
        report.getTopCoutries()
                .forEach(this::printCountryEntry);

        System.out.println("\nCountries with the least airports:");
        report.getBottomCountries()
                .forEach(this::printCountryEntry);

        System.out.println("\nMost common runway latitudes:");
        report.getCommonLatitudes()
                .forEach(this::printLatitudeEntry);
    }

    private void printCountryEntry(CountryReportEntry entry) {
        String countryName = entry.getCountry().getName();
        Long airportCount = entry.getAirportCount();
        String surfacesStr = Joiner.on(", ").join(entry.getSurfaceTypes());
        System.out.printf("%30s %6d   %s\n", countryName, airportCount, surfacesStr);
    }

    private void printLatitudeEntry(LatitudeReportEntry entry) {
        System.out.printf("%5s %6d\n", entry.getLatitudeId(), entry.getCount());
    }

}
