package com.example.demo.cache;

import com.example.demo.common.Entity;

public class CachedEntity {

    protected Entity entity;

    protected Object originalPersistentState;

    public CachedEntity(Entity entity,boolean storeState){
        this.entity = entity;
        if(storeState){
            this.originalPersistentState = entity.getPersistentState();
        }
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

}
