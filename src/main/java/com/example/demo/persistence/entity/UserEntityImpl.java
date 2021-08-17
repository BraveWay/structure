package com.example.demo.persistence.entity;

import com.example.demo.core.entity.Entity;
import com.example.demo.core.entity.AbstractEntity;

public class UserEntityImpl extends AbstractEntity implements UserEntity,User, Entity {

    protected String name ;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getPersistentState() {
        return null;
    }
}
