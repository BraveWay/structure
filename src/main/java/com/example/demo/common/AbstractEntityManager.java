package com.example.demo.common;

import com.example.demo.ConfigurationImpl;
import com.example.demo.core.entity.Entity;
import com.example.demo.persistence.AbstractManager;

public abstract class AbstractEntityManager<EntityImpl extends Entity> extends AbstractManager implements EntityManager<EntityImpl> {

    public AbstractEntityManager(ConfigurationImpl configurationImpl) {
        super(configurationImpl);
    }

    protected abstract DataManager<EntityImpl> getDataManager();

    @Override
    public EntityImpl findById(String entityId) {
        return getDataManager().findById(entityId);
    }

    @Override
    public void insert(EntityImpl entity) {
        insert(entity,true);
    }

    @Override
    public void insert(EntityImpl entity, boolean fireCreateEvent) {
        getDataManager().insert(entity);
        if(fireCreateEvent){
            System.out.println("插入数据");
        }
    }
}
