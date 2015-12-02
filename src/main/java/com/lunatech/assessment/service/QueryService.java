package com.lunatech.assessment.service;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.lunatech.assessment.model.Airport;
import com.lunatech.assessment.model.Country;
import com.lunatech.assessment.model.Runway;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

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
    private static final int HEADER_WIDTH = 56;

    public void query() {
        String input = prompt("\nCountry?\n");
        Country country = countryService.findMatch(input);
        printCountry(country);

        List<Airport> airports = airportService.findByCountry(country);
        Map<String, List<Runway>> runways = runwayService.filterAndGroupByAirport(airports);
        printAirportTable(airports, runways);
    }

    private void printAirportTable(List<Airport> airports, Map<String, List<Runway>> runways) {
        printAirportTableHeader();
        for (Airport airport : airports) {
            System.out.println();
            printAirport(airport);

            List<Runway> runwaysInAirport = runways.get(airport.getId());
            if (runwaysInAirport != null) {
                System.out.println("Runways:");
                System.out.printf(RUNWAY_ROW_FORMAT, "Length", "Width", "Surface", "Latitude", "Longitude");
                runwaysInAirport.forEach(this::printRunway);
            }
        }
    }

    private void printAirportTableHeader() {
        System.out.println();
        System.out.println("Airports:");
        System.out.println(Strings.repeat("-", HEADER_WIDTH));
    }

    private void printCountry(Country country) {
        System.out.printf("\nMatch found: %s (%s)\n", country.getName(), country.getCode());
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
