package com.lunatech.assessment.service;

import com.google.common.base.Joiner;
import com.google.inject.Inject;
import com.lunatech.assessment.model.Airport;
import com.lunatech.assessment.model.Country;
import com.lunatech.assessment.model.report.CountryReportEntry;
import com.lunatech.assessment.model.report.LatitudeReportEntry;
import com.lunatech.assessment.model.report.Report;
import com.lunatech.assessment.service.entity.AirportService;
import com.lunatech.assessment.service.entity.CountryService;
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

    public final int ENTRIES_COUNT;
    public static final String COUNTRY_ROW_FORMAT = "%30s %15s   %s\n";
    public static final String LATITUDE_ROW_FORMAT = "%30s %15s\n";

    @Inject private AirportService airportService;
    @Inject private CountryService countryService;
    @Inject private RunwayService runwayService;

    public ReportService() {
        ENTRIES_COUNT = 10;
    }

    public ReportService(int numberOfResults) {
        ENTRIES_COUNT = numberOfResults;
    }

    public void report() {
        Report report = createReport();
        printReport(report);
    }

    public Report createReport() {
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
        Map<String, Long> airportCountByCountry = airportService.countByCountryCode();
        return airportCountByCountry.entrySet().stream()
                .map(this::createCountryReportEntry)
                .sorted(comparing(CountryReportEntry::getAirportCount))
                .collect(toList());
    }

    private CountryReportEntry createCountryReportEntry(Map.Entry<String, Long> entry) {
        Country country = countryService.getById(entry.getKey());
        return new CountryReportEntry(country, entry.getValue());
    }

    private void setSurfaceTypesForCountries(Report report) {
        Stream<CountryReportEntry> allEntries = concat(report.getBottomCountries().stream(),
                report.getTopCoutries().stream());
        allEntries.forEach(entry ->
                entry.setSurfaceTypes(getSurfaceTypesForCountry(entry.getCountry())));
    }

    private List<String> getSurfaceTypesForCountry(Country country) {
        List<Airport> airports = airportService.findByCountry(country);
        return airports.stream()
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
        System.out.println("\n\nCountries with the most airports:\n");
        printCountryHeader();
        report.getTopCoutries()
                .forEach(this::printCountryEntry);

        System.out.println("\n\nCountries with the least airports:\n");
        printCountryHeader();
        report.getBottomCountries()
                .forEach(this::printCountryEntry);

        System.out.println("\n\nMost common runway latitudes:\n");
        printLatitudeHeader();
        report.getCommonLatitudes()
                .forEach(this::printLatitudeEntry);
    }

    private void printCountryHeader() {
        System.out.printf(COUNTRY_ROW_FORMAT, "Country", "Airport count", "Types of runways");
    }

    private void printLatitudeHeader() {
        System.out.printf(LATITUDE_ROW_FORMAT, "Latitude", "Runway count");
    }

    private void printCountryEntry(CountryReportEntry entry) {
        String countryName = entry.getCountry().getName();
        String airportCount = String.format("%6d", entry.getAirportCount());
        String surfacesStr = Joiner.on(", ").join(entry.getSurfaceTypes());
        System.out.printf(COUNTRY_ROW_FORMAT, countryName, airportCount, surfacesStr);
    }

    private void printLatitudeEntry(LatitudeReportEntry entry) {
        String count = String.format("%6d", entry.getCount());
        System.out.printf(LATITUDE_ROW_FORMAT, entry.getLatitudeId(), count);
    }

}
