package com.example.demo.common;

import com.example.demo.ConfigurationImpl;
import org.apache.ibatis.session.SqlSession;

public abstract class AbstractQueryManager<EntityImpl extends Entity> extends AbstractManager implements  QueryManager<EntityImpl>{

    public AbstractQueryManager(ConfigurationImpl configurationImpl) {
        super(configurationImpl);
    }

    @Override
    public long count(){

      return 0;
    }


}
