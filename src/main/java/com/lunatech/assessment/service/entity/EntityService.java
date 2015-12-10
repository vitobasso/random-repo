package com.lunatech.assessment.service.entity;

import com.lunatech.assessment.reader.EntityReader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * Created by Victor on 02/12/2015.
 */
public abstract class EntityService<T> {

    protected List<T> entities;
    protected Map<String, T> mapById;
    protected final EntityReader<T> reader;

    protected EntityService(EntityReader<T> reader) {
        this.reader = reader;
    }

    public List<T> listAll() {
        if (entities == null) {
            loadEntities(reader);
        }
        return entities;
    }

    public T getById(String key) {
        if (mapById == null) {
            loadEntities(reader);
        }
        return mapById.get(key);
    }

    protected Map<String, T> getMapById() {
        if (mapById == null) {
            loadEntities(reader);
        }
        return mapById;
    }

    protected void loadEntities(EntityReader<T> reader) {
        try {
            entities = reader.read();
            mapById = createMapById(entities);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, T> createMapById(List<T> entities) {
        return entities.stream()
                .collect(toMap(this::getId, x -> x));
    }

    protected abstract String getId(T entity);

}
