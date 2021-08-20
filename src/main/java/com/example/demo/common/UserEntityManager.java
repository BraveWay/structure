package com.example.demo.common;

import com.example.demo.persistence.entity.User;
import com.example.demo.persistence.entity.UserEntity;
import com.example.demo.query.UserQueryImpl;

import java.util.List;

public interface UserEntityManager extends EntityManager<UserEntity>{

    long findUserCountByQueryCriteria(UserQueryImpl userQuery);

    List<User> findUsersByQueryCriteria(UserQueryImpl userQuery);
}
