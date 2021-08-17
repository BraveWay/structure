package com.example.demo.persistence.cache;

import com.example.demo.core.entity.Entity;
import com.example.demo.core.Session;

import java.util.Map;

public interface EntityCache extends Session {

    Map<Class<?>, Map<String,CachedEntity>>  getAllCachedEntities();

    CachedEntity put(Entity entity, boolean storeState);

   <T> T findInCache(Class<T> entityClass,String id);
}
