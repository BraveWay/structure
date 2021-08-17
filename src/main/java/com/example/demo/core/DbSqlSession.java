package com.example.demo.core;

import com.example.demo.persistence.cache.EntityCache;
import com.example.demo.core.entity.Entity;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbSqlSession implements  Session{

    private static final Logger log = LoggerFactory.getLogger(DbSqlSession.class);

    protected SqlSession sqlSession;
    protected DbSqlSessionFactory dbSqlSessionFactory;
    protected EntityCache entityCache;

    public DbSqlSession(DbSqlSessionFactory dbSqlSessionFactory,EntityCache entityCache){
        this.dbSqlSessionFactory = dbSqlSessionFactory;
        this.sqlSession = dbSqlSessionFactory.getSqlSessionFactory().openSession();
    }

    public Object selectOne(String statement,Object parameter){
        System.out.println("statement===="+statement);
        statement = dbSqlSessionFactory.mapStatement(statement);
        Object result = sqlSession.selectOne(statement,parameter);
        return result;
    }

    public <T extends Entity> T selectById(Class<T> entityClass,String id){
        return selectById(entityClass,id,true);
    }

    public <T extends Entity> T selectById(Class<T> entityClass,String id,boolean useCache){
        T entity = null;
        if(useCache){
            entity = entityCache.findInCache(entityClass,id);
            if(entity != null){
                return entity;
            }
        }

        String selectStatement = dbSqlSessionFactory.getSelectStatement(entityClass);
        selectStatement = dbSqlSessionFactory.mapStatement(selectStatement);
        entity = (T) sqlSession.selectOne(selectStatement,id);
        if(entity == null){
            return null;
        }
        entityCache.put(entity,true);
        return entity;
    }




    @Override
    public void flush() {

    }

    @Override
    public void close() {

    }
}
