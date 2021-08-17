package com.example.demo.common;

import com.example.demo.core.entity.Entity;

public interface EntityManager<EntityImpl extends Entity>{

    EntityImpl findById(String entityId);

    void insert(EntityImpl entity);

    void insert(EntityImpl entity, boolean fireCreateEvent);


}
