package com.example.demo.persistence.entity.data.impl;

import com.example.demo.ConfigurationImpl;
import com.example.demo.common.UserDataManager;
import com.example.demo.persistence.entity.User;
import com.example.demo.persistence.entity.UserEntity;
import com.example.demo.persistence.entity.UserEntityImpl;
import com.example.demo.persistence.entity.data.AbstractDataManager;
import com.example.demo.query.UserQueryImpl;

import java.util.List;

public class MybatisUserDataManager extends AbstractDataManager<UserEntity> implements UserDataManager {

    public MybatisUserDataManager(ConfigurationImpl config) {
        super(config);
    }


    @Override
    public Class<? extends UserEntity> getManagedEntityClass() {
        return UserEntityImpl.class;
    }

    @Override
    public List<User> findUserByName(String name) {
        return null;
//        return this.getSession().selectList("com.example.demo.dao.UserDao.selectByName",name);
    }

    @Override
    public long findUserCountByQueryCriteria(UserQueryImpl userQuery) {
        return (Long) getDbSqlSession().selectOne("selectUserCountByQueryCriteria",userQuery);
    }

    @Override
    public List<User> findUsersByQueryCriteria(UserQueryImpl userQuery) {
        final String query = "selectUsersByQueryCriteria";
        return getDbSqlSession().selectList(query,userQuery);
    }

    @Override
    public void insert(UserEntity entity) {

    }
}
