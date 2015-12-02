package com.lunatech.assessment.service;

import com.lunatech.assessment.model.Airport;
import com.lunatech.assessment.model.Runway;
import com.lunatech.assessment.reader.RunwayReader;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Victor on 02/12/2015.
 */
public class RunwayService extends BaseService<Runway> {

    public RunwayService() {
        super(new RunwayReader());
    }

    public Map<String, List<Runway>> filterAndGroupByAirport(List<Airport> airports) {
        return listAll().stream()
                .filter(matchesAirports(airports))
                .collect(Collectors.groupingBy(Runway::getAirportRef));
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
