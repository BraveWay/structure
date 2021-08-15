package com.example.demo.common;

public interface DataManager<EntityImpl extends Entity> {

    EntityImpl findById(String entityId);

}
