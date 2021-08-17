package com.example.demo.common;

import com.example.demo.core.entity.Entity;

public interface QueryManager<EntityImpl extends Entity> {

    long count();
}
