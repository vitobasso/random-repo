package com.lunatech.assessment.reader;

import com.lunatech.assessment.model.Runway;
import com.lunatech.assessment.util.Record;

/**
 * Created by Victor on 01/12/2015.
 */
public class RunwayReader extends EntityReader<Runway> {

    public RunwayReader() {
        super("runways.csv");
    }

    protected Runway read(Record record) {
        Runway runway = new Runway();
        runway.setId(record.get("id"));
        runway.setAirportRef(record.get("airport_ref"));
        runway.setLength(record.getInt("length_ft"));
        runway.setWidth(record.getInt("width_ft"));
        runway.setSurface(record.get("surface"));
        runway.setLatitude(record.get("le_ident"));
        return runway;
    }

}
