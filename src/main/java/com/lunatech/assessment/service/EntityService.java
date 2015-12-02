package com.lunatech.assessment.service;

import com.lunatech.assessment.reader.EntityReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor on 02/12/2015.
 */
public abstract class EntityService<T> {

    protected List<T> entities;
    protected final EntityReader<T> reader;

    protected EntityService(EntityReader<T> reader) {
        this.reader = reader;
    }

    public List<T> listAll() {
        if (entities == null) {
            entities = loadEntities(reader);
        }
        return entities;
    }

    protected List<T> loadEntities(EntityReader<T> reader) {
        try {
            return reader.read();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
