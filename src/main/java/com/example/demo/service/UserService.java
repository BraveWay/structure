package com.example.demo.service;

import com.example.demo.persistence.entity.User;
import com.example.demo.query.UserQuery;

public interface UserService {

    UserQuery createUserQuery();

    User findById(String id);

}
