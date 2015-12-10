package com.lunatech.assessment.service.entity;

import com.google.inject.Inject;
import com.lunatech.assessment.model.Airport;
import com.lunatech.assessment.model.Runway;
import com.lunatech.assessment.reader.RunwayReader;

import java.util.List;
import java.util.Map;

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
        return airports.stream()
                .collect(toMap(Airport::getId, this::findByAirport));
    }

    public List<Runway> findByAirport(Airport airport) {
        return getByParentId(airport.getId());
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
