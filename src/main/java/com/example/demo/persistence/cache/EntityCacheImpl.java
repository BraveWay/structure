package com.example.demo.persistence.cache;

import com.example.demo.core.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class EntityCacheImpl  implements EntityCache{

    protected Map<Class<?>, Map<String, CachedEntity>> cachedObjects = new HashMap<Class<?>, Map<String,CachedEntity>>();

    @Override
    public Map<Class<?>, Map<String, CachedEntity>> getAllCachedEntities() {
        return cachedObjects;
    }

    @Override
    public CachedEntity put(Entity entity, boolean storeState) {
        Map<String, CachedEntity> classCache = cachedObjects.computeIfAbsent(entity.getClass(), k -> new HashMap<String, CachedEntity>());
        CachedEntity cachedObject = new CachedEntity(entity, storeState);
        classCache.put(entity.getId(), cachedObject);
        return cachedObject;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T findInCache(Class<T> entityClass, String id) {
        CachedEntity cachedObject = null;
        Map<String, CachedEntity> classCache = cachedObjects.get(entityClass);

        if (classCache == null) {
            classCache = findClassCacheByCheckingSubclasses(entityClass);
        }

        if (classCache != null) {
            cachedObject = classCache.get(id);
        }

        if (cachedObject != null) {
            return (T) cachedObject.getEntity();
        }

        return null;
    }

    protected Map<String,CachedEntity> findClassCacheByCheckingSubclasses(Class<?> entityClass){
        for(Class<?> clazz : cachedObjects.keySet()){
            if(entityClass.isAssignableFrom(clazz)){
                return cachedObjects.get(clazz);
            }
        }
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() {

    }
}
