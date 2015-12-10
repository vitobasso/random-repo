package com.lunatech.assessment.service.entity;

import com.google.inject.Inject;
import com.lunatech.assessment.model.Airport;
import com.lunatech.assessment.model.Country;
import com.lunatech.assessment.reader.AirportReader;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * Created by Victor on 02/12/2015.
 */
public class AirportService extends ChildEntityService<Airport> {

    @Inject
    public AirportService(AirportReader reader) {
        super(reader);
    }

    @Override
    protected String getId(Airport entity) {
        return entity.getId();
    }

    @Override
    protected String getParentId(Airport entity) {
        return entity.getCountryCode();
    }

    public List<Airport> findByCountry(Country country) {
        return getByParentId(country.getCode());
    }

    public Map<String, Long> countByCountryCode() {
        return listAll().stream()
                .collect(groupingBy(Airport::getCountryCode, counting()));
    }

}
