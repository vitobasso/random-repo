package com.lunatech.assessment.service.entity;

import com.google.inject.Inject;
import com.lunatech.assessment.model.Airport;
import com.lunatech.assessment.model.Runway;
import com.lunatech.assessment.reader.RunwayReader;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.stream.Collectors.*;

/**
 * Created by Victor on 02/12/2015.
 */
public class RunwayService extends ChildEntityService<Runway> {

    @Inject
    public RunwayService(RunwayReader reader) {
        super(reader);
    }

    @Override
    protected String getId(Runway entity) {
        return entity.getId();
    }

    @Override
    protected String getParentId(Runway entity) {
        return entity.getAirportRef();
    }

    public Map<String, List<Runway>> groupByAirport(List<Airport> airports) {
        return listAll().parallelStream()
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

    public List<Runway> findByAirport(Airport airport) {
        return listAll().parallelStream()
                .filter(belongsToAirport(airport))
                .collect(toList());
    }

    private Predicate<Runway> belongsToAirport(Airport airport) {
        return runway -> airport.getId().equals(runway.getAirportRef());
    }

    public List<String> getRunwaySurfaceTypes(Airport airport) {
        return findByAirport(airport).stream()
                .map(Runway::getSurface)
                .collect(toList());
    }

    public Map<String, Long> countByLatitudeCircle() {
        return listAll().stream()
                .collect(groupingBy(Runway::getLatitude, counting()));
    }

}
