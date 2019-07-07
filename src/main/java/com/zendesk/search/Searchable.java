package com.zendesk.search;

import java.util.List;
import java.util.Set;

public interface Searchable<T,ID> {

    T searchById(ID id);

    List<T> searchByTermValue(String term, String value);

    Set<String> getSearchableFields();


}
