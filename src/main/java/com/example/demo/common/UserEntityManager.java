package com.example.demo.common;

import com.example.demo.entity.UserEntity;
import com.example.demo.query.UserQueryImpl;

public interface UserEntityManager extends EntityManager<UserEntity>{

    long findUserCountByQueryCriteria(UserQueryImpl userQuery);
}