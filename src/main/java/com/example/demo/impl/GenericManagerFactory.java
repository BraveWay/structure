package com.example.demo.impl;

import com.example.demo.core.CommandContext;
import com.example.demo.core.Session;
import com.example.demo.core.SessionFactory;

public class GenericManagerFactory implements SessionFactory {

    protected Class<? extends Session> typeClass;
    protected Class<? extends Session> implementationClass;

    public GenericManagerFactory(Class<? extends Session> typeClass, Class<? extends Session> implementationClass) {
        this.typeClass = typeClass;
        this.implementationClass = implementationClass;
    }

    public GenericManagerFactory(Class<? extends Session> implementationClass) {
        this(implementationClass, implementationClass);
    }

    public Class<?> getSessionType() {
        return typeClass;
    }

    @Override
    public Session openSession(CommandContext commandContext) {
        try {
            return implementationClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
