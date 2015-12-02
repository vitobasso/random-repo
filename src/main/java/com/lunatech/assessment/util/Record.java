package com.lunatech.assessment.util;

import org.apache.commons.csv.CSVRecord;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Victor on 01/12/2015.
 */
public class Record implements Iterable<String> {

    private CSVRecord record;

    public Record(CSVRecord record) {
        this.record = record;
    }

    public String get(String name) {
        return record.get(name);
    }

    public String getString(String name) {
        return record.get(name);
    }

    public Double getDouble(String name) {
        return getValue(Double::valueOf, name);
    }

    public Integer getInt(String name) {
        return getValue(Integer::valueOf, name);
    }

    public Boolean getBoolean(String name) {
        return getValue(value -> value.equals("1"), name);
    }

    private <T> T getValue(Function<String, T> convert, String name) {
        try {
            String rawValue = record.get(name);
            if (rawValue != null && !rawValue.isEmpty()) {
                return convert.apply(rawValue);
            } else {
                return null;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Iterator<String> iterator() {
        return record.iterator();
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        record.forEach(action);
    }

    @Override
    public Spliterator<String> spliterator() {
        return record.spliterator();
    }

}
