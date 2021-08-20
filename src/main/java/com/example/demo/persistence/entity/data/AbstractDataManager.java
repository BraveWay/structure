package com.example.demo.persistence.entity.data;

import com.example.demo.ConfigurationImpl;
import com.example.demo.persistence.cache.EntityCache;
import com.example.demo.persistence.AbstractManager;
import com.example.demo.common.DataManager;
import com.example.demo.core.DbSqlSession;
import com.example.demo.core.entity.Entity;

import java.util.List;

public abstract class AbstractDataManager<EntityImpl extends Entity> extends AbstractManager implements DataManager<EntityImpl> {


    public AbstractDataManager(ConfigurationImpl configurationImpl) {
        super(configurationImpl);
    }

    public abstract  Class<? extends EntityImpl> getManagedEntityClass();

    public List<Class<? extends EntityImpl>> getManagedEntitySubClasses(){
        return null;
    }

    protected DbSqlSession getDbSqlSession(){
        return getSession(DbSqlSession.class);
    }

    protected EntityCache getEntityCache(){
        return getSession(EntityCache.class);
    }

    @Override
    public EntityImpl findById(String entityId) {
        if(entityId == null){
            return null;
        }

        // Cache
        EntityImpl cachedEntity = getEntityCache().findInCache(getManagedEntityClass(),entityId);
        if(cachedEntity != null){
            return cachedEntity;
        }

        //Database
        return getDbSqlSession().selectById(getManagedEntityClass(),entityId,false);

    }
}
