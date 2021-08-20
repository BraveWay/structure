package com.example.demo.core;

import com.example.demo.core.query.ListQueryParameterObject;
import com.example.demo.persistence.cache.CachedEntity;
import com.example.demo.persistence.cache.EntityCache;
import com.example.demo.core.entity.Entity;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @SuppressWarnings("unchecked")
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

    public List selectList(String statement,ListQueryParameterObject parameter){
        return selectList(statement,parameter,true);
    }

    public List selectList(String statement,ListQueryParameterObject parameter,boolean useCache){
        return selectListWithRawParameter(statement,parameter,parameter.getFirstResult(),parameter.getMaxResults(),useCache);
    }

    public <T extends Entity> List<T> selectListWithRawParameter(String statement, Object parameter,int firstResult,int maxResults, boolean useCache){
        statement = dbSqlSessionFactory.mapStatement(statement);
        if(firstResult == -1 || maxResults == -1){
            return Collections.EMPTY_LIST;
        }

        List loadedObjects = sqlSession.selectList(statement,parameter);
        if(useCache){
            return cachedLoadOrStore(loadedObjects);
        }else {
            return loadedObjects;
        }
    }

    protected List cachedLoadOrStore(List<Object> loadedObjects){
        if(loadedObjects.isEmpty()){
            return loadedObjects;
        }
        if(!(loadedObjects.get(0) instanceof Entity)){
            return loadedObjects;
        }

        List<Entity> filteredObjects = new ArrayList<Entity>(loadedObjects.size());
        for(Object loadedObject : loadedObjects){
            Entity cachedEntity =  cachedLoadOrStore((Entity) loadedObject);
            filteredObjects.add(cachedEntity);
        }
        return filteredObjects;
    }

    protected Entity cachedLoadOrStore(Entity entity){
        Entity cachedEntity = entityCache.findInCache(entity.getClass(),entity.getId());

        if(cachedEntity != null){
            return cachedEntity;
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
