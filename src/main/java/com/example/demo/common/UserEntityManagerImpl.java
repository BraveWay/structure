package com.example.demo.common;

import com.example.demo.ConfigurationImpl;
import com.example.demo.persistence.entity.UserEntity;
import com.example.demo.persistence.entity.data.AbstractDataManager;
import com.example.demo.query.UserQueryImpl;

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
    public void insert(UserEntity entity, boolean fireCreateEvent) {
        super.insert(entity, fireCreateEvent);
    }
}
