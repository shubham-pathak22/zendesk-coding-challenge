package com.zendesk.search;

import java.util.List;
import java.util.Set;

/**
 * Interface defining search operations.
 * @param <T> the type of elements to be searched
 * @param <ID> the data type of the ID
 * @see Dictionary
 */
public interface Searchable<T,ID> {

    /**
     * Returns the object for the given id
     * @param id id of the object
     * @return Object having the given id
     */
    T searchById(ID id);

    /**
     * Returns the list of objects that has the given value for the term
     * @param term attribute/key field
     * @param value value to be searched
     * @return {@link List} of objects
     */
    List<T> searchByTermValue(String term, String value);

    /**
     * Returns the names of the fields that can be searched for as a {@link Set}
     * @return {@link Set} containing names of fields that can be searched
     */
    Set<String> getSearchableFields();
}
