package org.sbsa.deco.mappers.converters;

import org.modelmapper.AbstractConverter;

import java.util.List;

public class ObjectsListToObjectsCountConverter extends AbstractConverter<List<Object>, Integer> {

    @Override
    protected Integer convert(List<Object> objectList) {
        if(objectList != null) {
            return objectList.size();
        } else {
            return 0;
        }
    }
}