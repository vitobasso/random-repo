package com.lunatech.assessment.service;

import com.google.inject.Inject;
import com.lunatech.assessment.model.Airport;
import com.lunatech.assessment.model.Country;
import com.lunatech.assessment.model.Runway;

import java.util.List;
import java.util.Map;

import static com.lunatech.assessment.util.Lang.prompt;

/**
 * Created by Victor on 02/12/2015.
 */
public class QueryService {

    @Inject private CountryService countryService;
    @Inject private AirportService airportService;
    @Inject private RunwayService runwayService;

    public void query() {
        String input = prompt("\nCountry?\n");
        Country country = countryService.findMatch(input);
        printCountry(country);

        List<Airport> airports = airportService.findByCountry(country);
        Map<String, List<Runway>> runways = runwayService.filterAndGroupByAirport(airports);
        printAirportTable(airports, runways);
    }

    private void printAirportTable(List<Airport> airports, Map<String, List<Runway>> runways) {
        System.out.println();
        for (Airport airport : airports) {
            printAirport(airport);

            List<Runway> runwaysInAirport = runways.get(airport.getId());
            if (runwaysInAirport != null)
                runwaysInAirport.forEach(this::printRunway);
        }
    }

    private void printCountry(Country country) {
        System.out.printf("%s (%s)\n", country.getName(), country.getCode());
    }

    private void printAirport(Airport airport) {
        System.out.println(airport.getName());
    }

    private void printRunway(Runway runway) {
        System.out.printf("%8s %8s %10s %13s %13s\n",
                runway.getLength(), runway.getWidth(), runway.getSurface(),
                formatCoordinate(runway.getLatitude()), formatCoordinate(runway.getLongitude()));
    }

    private String formatCoordinate(Double coordinate) {
        return coordinate != null ? String.format("%3.10f", coordinate) : "";
    }

}
