package com.lunatech.assessment.reader;

import com.lunatech.assessment.model.Country;
import com.lunatech.assessment.util.Record;

/**
 * Created by Victor on 01/12/2015.
 */
public class CountryReader extends EntityReader<Country> {

    public CountryReader() {
        super("countries.csv");
    }

    protected Country read(Record record) {
        Country country = new Country();
        country.setCode(record.get("code"));
        country.setName(record.get("name"));
        return country;
    }

}
