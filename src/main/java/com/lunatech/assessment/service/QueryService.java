package com.lunatech.assessment.service;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.lunatech.assessment.model.Airport;
import com.lunatech.assessment.model.Country;
import com.lunatech.assessment.model.Runway;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.lunatech.assessment.util.Lang.format;
import static com.lunatech.assessment.util.Lang.prompt;

/**
 * Created by Victor on 02/12/2015.
 */
public class QueryService {

    @Inject private CountryService countryService;
    @Inject private AirportService airportService;
    @Inject private RunwayService runwayService;

    private static final String RUNWAY_ROW_FORMAT = "%8s %8s %10s %13s %13s\n";
    private static final DecimalFormat COORDINATE_FORMAT = new DecimalFormat("###.#");

    public void query() {
        Optional<Country> country = findCountryForUserInput();
        country.ifPresent(this::gatherDataAndShow);
    }

    private Optional<Country> findCountryForUserInput() {
        String input = prompt("\nCountry?\n");
        Optional<Country> country = countryService.findFuzzy(input);
        printFeedbackForFind(input, country.orElse(null));
        return country;
    }

    private void gatherDataAndShow(Country country) {
        List<Airport> airports = airportService.findByCountry(country);
        Map<String, List<Runway>> runways = runwayService.groupByAirport(airports);
        printAirportTable(airports, runways);
    }

    private void printFeedbackForFind(String input, Country country) {
        if (country != null) {
            System.out.printf("\nFound: %s (%s)\n", country.getName(), country.getCode());
        } else {
            System.out.printf("Couldn't find a country matching \"%s\"\n", input);
        }
    }

    private void printAirportTable(List<Airport> airports, Map<String, List<Runway>> runways) {
        printMainHeader();
        for (Airport airport : airports) {
            System.out.println();
            printAirport(airport);
            printRunways(runways.get(airport.getId()));
        }
    }

    private void printRunways(List<Runway> runwaysInAirport) {
        if (runwaysInAirport != null) {
            printRunwayHeader();
            runwaysInAirport.forEach(this::printRunway);
        }
    }

    private void printMainHeader() {
        System.out.println();
        System.out.println("Airports:");
        System.out.println(Strings.repeat("-", 56));
    }

    private void printRunwayHeader() {
        System.out.println("Runways:");
        System.out.printf(RUNWAY_ROW_FORMAT, "Length", "Width", "Surface", "Latitude", "Longitude");
    }

    private void printAirport(Airport airport) {
        System.out.println(airport.getName());
    }

    private void printRunway(Runway runway) {
        System.out.printf(RUNWAY_ROW_FORMAT,
                format(runway.getLength()),
                format(runway.getWidth()),
                format(runway.getSurface()),
                formatCoordinate(runway.getLatitude()),
                formatCoordinate(runway.getLongitude()));
    }

    private String formatCoordinate(Double coordinate) {
        return coordinate != null? COORDINATE_FORMAT.format(coordinate.doubleValue()) : "";
    }

}
