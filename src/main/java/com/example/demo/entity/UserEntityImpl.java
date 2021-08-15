package com.example.demo.entity;

import com.example.demo.common.Entity;

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
