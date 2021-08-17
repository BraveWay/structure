package com.example.demo.common;

import com.example.demo.ConfigurationImpl;
import com.example.demo.core.entity.Entity;
import com.example.demo.persistence.AbstractManager;

public abstract class AbstractQueryManager<EntityImpl extends Entity> extends AbstractManager implements  QueryManager<EntityImpl>{

    public AbstractQueryManager(ConfigurationImpl configurationImpl) {
        super(configurationImpl);
    }

    @Override
    public long count(){

      return 0;
    }


}
