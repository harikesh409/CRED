package com.crio.cred.util;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Utils.
 *
 * @author nithesh.tarani
 * @author harikesh.pallantla
 */
public class Utils {

    /**
     * Utility method to map list of elements to a particular class.
     *
     * @param <S>         the generic type of source class
     * @param <T>         the generic type target class
     * @param modelMapper the model mapper
     * @param source      the source
     * @param targetClass the target class
     * @return the list
     */
    public static <S, T> List<T> mapList(ModelMapper modelMapper, List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    /**
     * Maps the Page {@code entities} of <code>T</code> type which have to be mapped as input to {@code dtoClass} Page
     * of mapped object with <code>D</code> type.
     *
     * @param <D>         type of objects in result page
     * @param <T>         type of entity in <code>entityPage</code>
     * @param modelMapper the modelMapper
     * @param entities    page of entities that needs to be mapped
     * @param dtoClass    class of result page element
     * @return page - mapped page with objects of type <code>D</code>.
     */
    public static <D, T> Page<D> mapEntityPageIntoDtoPage(ModelMapper modelMapper, Page<T> entities, Class<D> dtoClass) {
        return entities.map(objectEntity -> modelMapper.map(objectEntity, dtoClass));
    }
}
