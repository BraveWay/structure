package com.example.demo.entity;

import com.example.demo.common.Entity;

public abstract class AbstractEntity implements Entity {

    protected Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
