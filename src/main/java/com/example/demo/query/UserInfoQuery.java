package com.example.demo.query;

import com.example.demo.core.query.Query;
import com.example.demo.persistence.entity.User;

import java.util.List;

public interface UserInfoQuery<T extends UserInfoQuery<?,?>,V extends User> extends Query<T,V> {

    T userId(String userId);

    T taskName(String name);

    T taskNameIn(List<String> nameList);
}
