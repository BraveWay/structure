package com.example.demo.common;

import com.example.demo.core.CommandContext;
import com.example.demo.core.DbSqlSession;
import com.example.demo.core.Session;
import com.example.demo.core.SessionFactory;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DbSqlSessionFactory implements SessionFactory {

    protected SqlSessionFactory sqlSessionFactory;
    protected Map<String,String> statementMappings;

    protected Map<Class<?>,String> selectStatements = new ConcurrentHashMap<Class<?>, String>();

    public String mapStatement(String statement){
        if(statementMappings == null){
            return statement;
        }
        String mappedStatement = statementMappings.get(statement);
        return (mappedStatement != null ? mappedStatement : statement);
    }

    public String getSelectStatement(Class<?> entityClass){
        return getStatement(entityClass, selectStatements, "select");
    }

    private String getStatement(Class<?> entityClass,Map<Class<?>,String> cachedStatements,String prefix){
        String statement = cachedStatements.get(entityClass);
        if(statement != null){
            return statement;
        }
        statement = prefix+entityClass.getSimpleName();
        if(statement.endsWith("Impl")){
            statement = statement.substring(0,statement.length() - 10); // removing 'entityImpl'
        }else if(statement.endsWith("entity")){
            statement = statement.substring(0,statement.length() - 6); // removing 'entity'
        }
        cachedStatements.put(entityClass,statement);
        return statement;
    }

    //getters and setters ////////
    public SqlSessionFactory getSqlSessionFactory(){
        return sqlSessionFactory;
    }

    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public Class<?> getSessionType() {
        return DbSqlSession.class;
    }

    @Override
    public Session openSession(CommandContext commandContext) {
        DbSqlSession dbSqlSession = new DbSqlSession(this, commandContext.getEntityCache());

        return dbSqlSession;
    }
}
