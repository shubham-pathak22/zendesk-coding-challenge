package com.zendesk.search;

import java.util.List;
import java.util.Set;

public interface Searchable<T> {

    T searchById(String id);

    List<T> searchByTermValue(String term, String value);

    Set<String> getSearchableFields();
}
