package com.example.demo.core;

import com.example.demo.ConfigurationImpl;
import com.example.demo.cache.EntityCache;
import com.example.demo.common.UserEntityManager;
import com.example.demo.interceptor.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class CommandContext {

    private static Logger log = LoggerFactory.getLogger(CommandContext.class);
    protected Map<Class<?>, SessionFactory> sessionFactories;
    protected Map<Class<?>,Session> sessions = new HashMap<>();
    protected ConfigurationImpl config;

    protected LinkedList<Object> resultStack = new LinkedList<>();

    public CommandContext(Command<?> cmd,ConfigurationImpl config){
        this.config = config;
        this.sessionFactories = config.getSessionFactories();
    }

    public DbSqlSession getDbSqlSession() {
        return getSession(DbSqlSession.class);
    }

    public EntityCache getEntityCache() {
        return getSession(EntityCache.class);
    }

    public UserEntityManager getUserEntityManager(){
        return config.getUserEntityManager();
    }

    public <T> T getSession(Class<T> sessionClass){
        Session session = sessions.get(sessionClass);
        if(session == null){
            SessionFactory sessionFactory = sessionFactories.get(sessionClass);
            if(sessionFactory == null){
                throw new RuntimeException("no session factory configured for " + sessionClass.getName());
            }
            session = sessionFactory.openSession(this);
            sessions.put(sessionClass,session);
        }
        return (T) session;
    }

    public void close(){

    }

    public Object getResult() {
        return resultStack.pollLast();
    }

    public void setResult(Object result) {
        resultStack.add(result);
    }
}
