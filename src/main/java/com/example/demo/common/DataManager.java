package com.example.demo.common;

import com.example.demo.core.entity.Entity;

public interface DataManager<EntityImpl extends Entity> {

    EntityImpl findById(String entityId);

    void insert(EntityImpl entity);
}
