package com.lunatech.assessment.reader;

import com.lunatech.assessment.model.Airport;
import com.lunatech.assessment.util.Record;

/**
 * Created by Victor on 01/12/2015.
 */
public class AirportReader extends EntityReader<Airport> {

    public AirportReader() {
        super("airports.csv");
    }

    protected Airport read(Record record) {
        Airport airport = new Airport();
        airport.setId(record.get("id"));
        airport.setName(record.get("name"));
        airport.setCountryCode(record.get("iso_country"));
        return airport;
    }

}
