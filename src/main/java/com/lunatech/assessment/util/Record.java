package com.lunatech.assessment.util;

import org.apache.commons.csv.CSVRecord;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;

public class Record implements Iterable<String> {

    private CSVRecord csvRecord;

    public Record() {
    }

    public Record(CSVRecord csvRecord) {
        this.csvRecord = csvRecord;
    }

    public String get(String name) {
        return csvRecord.get(name);
    }

    public String getString(String name) {
        return get(name);
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
            String rawValue = get(name);
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
        return csvRecord.iterator();
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        csvRecord.forEach(action);
    }

    @Override
    public Spliterator<String> spliterator() {
        return csvRecord.spliterator();
    }

}
