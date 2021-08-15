package com.example.demo.common;

import com.example.demo.ConfigurationImpl;
import com.example.demo.entity.User;
import com.example.demo.entity.UserEntity;
import com.example.demo.entity.UserEntityImpl;
import com.example.demo.query.UserQueryImpl;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class MybatisUserDataManager extends AbstractDataManager<UserEntity> implements  UserDataManager {

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
        Long count = null;
        try{
            count = (Long) getDbSqlSession().selectOne("selectUserCountByQueryCriteria",userQuery);
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }
}
