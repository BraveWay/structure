package com.example.demo.common;

import com.example.demo.ConfigurationImpl;
import com.example.demo.cache.EntityCache;
import com.example.demo.core.DbSqlSession;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public abstract class AbstractDataManager<EntityImpl extends Entity> extends AbstractManager  implements DataManager<EntityImpl> {


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

        EntityImpl cachedEntity = getEntityCache().findInCache(getManagedEntityClass(),entityId);

        return getDbSqlSession().selectById(getManagedEntityClass(),entityId,false);

    }
}
