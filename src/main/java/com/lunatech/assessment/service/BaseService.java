package com.lunatech.assessment.service;

import com.lunatech.assessment.reader.BaseReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 02/12/2015.
 */
public abstract class BaseService<T> {

    protected List<T> entities;
    protected final BaseReader<T> reader;

    protected BaseService(BaseReader<T> reader) {
        this.reader = reader;
    }

    public List<T> listAll() {
        if (entities == null) {
            entities = loadEntities(reader);
        }
        return entities;
    }

    protected List<T> loadEntities(BaseReader<T> reader) {
        try {
            return reader.read();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
