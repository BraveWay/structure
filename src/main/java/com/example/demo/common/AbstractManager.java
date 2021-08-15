package com.example.demo.common;

import com.example.demo.ConfigurationImpl;
import com.example.demo.core.CommandContext;
import com.example.demo.impl.Context;
import org.apache.ibatis.session.SqlSession;

public abstract class AbstractManager {

    protected ConfigurationImpl configurationImpl;

    public AbstractManager(ConfigurationImpl configurationImpl){
        this.configurationImpl = configurationImpl;
    }

    protected CommandContext getCommandContext(){
        return Context.getCommandContext();
    }

    protected <T> T getSession(Class<T> sessionClass){
        return getCommandContext().getSession(sessionClass);
    }

    protected ConfigurationImpl getConfigurationImpl(){
        return configurationImpl;
    }

    protected UserEntityManager getUserEntity(){
        return getConfigurationImpl().getUserEntityManager();
    }



}
