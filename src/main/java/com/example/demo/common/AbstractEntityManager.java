package com.example.demo.common;

import com.example.demo.ConfigurationImpl;
import org.apache.ibatis.session.SqlSession;

public abstract class AbstractEntityManager<EntityImpl extends Entity> extends AbstractManager implements EntityManager<EntityImpl> {

    public AbstractEntityManager(ConfigurationImpl configurationImpl) {
        super(configurationImpl);
    }

}
