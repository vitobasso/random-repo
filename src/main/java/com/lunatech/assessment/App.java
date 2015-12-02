
package com.lunatech.assessment;

import com.google.inject.Inject;
import com.lunatech.assessment.model.Airport;
import com.lunatech.assessment.model.Country;
import com.lunatech.assessment.model.Runway;
import com.lunatech.assessment.model.report.Report;
import com.lunatech.assessment.service.AirportService;
import com.lunatech.assessment.service.CountryService;
import com.lunatech.assessment.service.ReportService;
import com.lunatech.assessment.service.RunwayService;

import java.util.List;
import java.util.Map;

import static com.lunatech.assessment.util.Lang.prompt;

public class App {

    @Inject private CountryService countryService;
    @Inject private AirportService airportService;
    @Inject private RunwayService runwayService;
    @Inject private ReportService reportService;

    public void begin() {
        String option = prompt("Choose:\n1) Query\n2) Reports\n");

        if ("1".equals(option)) {
            query();
        } else if ("2".equals(option)) {
            report();
        } else {
            System.out.println("Invalid option.");
        }
    }

    public void query() {
        String input = prompt("Country?\n");
        Country country = countryService.findMatch(input);
        printCountry(country);

        List<Airport> airports = airportService.findByCountry(country);
        Map<String, List<Runway>> runways = runwayService.filterAndGroupByAirport(airports);
        printAirportTable(airports, runways);
    }

    public void report() {
        Report report = reportService.createReport();

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
        System.out.printf("   %s %s %s %s %f %f\n",
                runway.getId(), runway.getLength(), runway.getWidth(), runway.getSurface(),
                runway.getLatitude(), runway.getLongitude());
    }

    private void printAirportCount(Country country, Long count) {
        System.out.printf("%s %d\n", country.getName(), count);
    }

}
