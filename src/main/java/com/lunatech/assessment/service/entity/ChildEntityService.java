package com.lunatech.assessment.service.entity;

import com.lunatech.assessment.reader.EntityReader;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created by Victor on 10/12/2015.
 */
public abstract class ChildEntityService<T> extends EntityService<T> {

    protected Map<String, List<T>> mapByParentId;

    protected ChildEntityService(EntityReader<T> reader) {
        super(reader);
    }

    public List<T> getByParentId(String parentId) {
        lazyLoad();
        List<T> children = mapByParentId.get(parentId);
        return children != null ? children : Collections.<T>emptyList();
    }

    private void lazyLoad() {
        if (mapByParentId == null) {
            mapByParentId = createMapByParentId(listAll());
        }
    }

    private Map<String, List<T>> createMapByParentId(List<T> entities) {
        return entities.stream()
                .collect(groupingBy(this::getParentId));
    }

    protected abstract String getParentId(T entity);

}
