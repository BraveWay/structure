package com.example.demo.query;

import java.util.List;

public interface Query<T extends Query<?,?>,U> {

    T asc();

    T desc();

    T orderBy(QueryProperty property);

    long count();

    U singleResult();

    List<U> list();

    List<U> listPage(int firstResult,int maxResults);

}
