package com.example.demo.common;

public interface EntityManager<EntityImpl extends Entity>{

    EntityImpl findById(String entityId);

}
