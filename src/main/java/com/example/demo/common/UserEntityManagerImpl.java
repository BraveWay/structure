package com.example.demo.common;

import com.example.demo.ConfigurationImpl;
import com.example.demo.persistence.entity.User;
import com.example.demo.persistence.entity.UserEntity;
import com.example.demo.query.UserQueryImpl;

import java.util.List;

public class UserEntityManagerImpl extends AbstractEntityManager<UserEntity> implements UserEntityManager{

    protected UserDataManager userDataManager;

    public UserEntityManagerImpl(ConfigurationImpl configurationImpl,UserDataManager userDataManager) {
        super(configurationImpl);
        this.userDataManager = userDataManager;
    }

    @Override
    protected DataManager<UserEntity> getDataManager() {
        return userDataManager;
    }

    @Override
    public long findUserCountByQueryCriteria(UserQueryImpl userQuery) {
        return userDataManager.findUserCountByQueryCriteria(userQuery);
    }

    @Override
    public List<User> findUsersByQueryCriteria(UserQueryImpl userQuery) {
        return userDataManager.findUsersByQueryCriteria(userQuery);
    }

    @Override
    public void insert(UserEntity entity, boolean fireCreateEvent) {
        super.insert(entity, fireCreateEvent);
    }
}
