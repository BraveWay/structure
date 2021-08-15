package com.example.demo.common;

import com.example.demo.ConfigurationImpl;
import com.example.demo.entity.User;
import com.example.demo.entity.UserEntity;
import com.example.demo.query.UserQueryImpl;
import org.apache.ibatis.session.SqlSession;

public class UserEntityManagerImpl extends AbstractDataManager<UserEntity> implements UserEntityManager{

    protected UserDataManager userDataManager;

    public UserEntityManagerImpl(ConfigurationImpl configurationImpl,UserDataManager userDataManager) {
        super(configurationImpl);
        this.userDataManager = userDataManager;
    }


    @Override
    public Class<? extends UserEntity> getManagedEntityClass() {
        return null;
    }

    @Override
    public long findUserCountByQueryCriteria(UserQueryImpl userQuery) {
        return userDataManager.findUserCountByQueryCriteria(userQuery);
    }
}
