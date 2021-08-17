package com.example.demo.common;

import com.example.demo.persistence.entity.User;
import com.example.demo.persistence.entity.UserEntity;
import com.example.demo.query.UserQueryImpl;

import java.util.List;

public interface UserDataManager extends DataManager<UserEntity>{

    List<User> findUserByName(String name);

    long findUserCountByQueryCriteria(UserQueryImpl userQuery);
}
