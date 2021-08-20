package com.example.demo.core.entity;

public abstract class AbstractEntity implements Entity {

    protected String id;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
