package com.lunatech.assessment.service;

import com.google.inject.Inject;
import com.lunatech.assessment.model.Airport;
import com.lunatech.assessment.model.Runway;
import com.lunatech.assessment.reader.RunwayReader;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created by Victor on 02/12/2015.
 */
public class RunwayService extends EntityService<Runway> {

    @Inject
    public RunwayService(RunwayReader reader) {
        super(reader);
    }

    public Map<String, List<Runway>> filterAndGroupByAirport(List<Airport> airports) {
        return listAll().stream()
                .filter(matchesAirports(airports))
                .collect(groupingBy(Runway::getAirportRef));
    }

    private Predicate<Runway> matchesAirports(List<Airport> airports) {
        return runway -> airports.stream()
                .filter(hasRunway(runway))
                .count() > 0;
    }

    private Predicate<Airport> hasRunway(Runway runway) {
        return airport -> airport.getId().equals(runway.getAirportRef());
    }

}
