package com.example.demo.query;

import com.example.demo.core.CommandContext;
import com.example.demo.core.query.AbstractVariableQueryImpl;
import com.example.demo.impl.Page;
import com.example.demo.persistence.entity.User;
import com.example.demo.interceptor.CommandExecutor;
import com.example.demo.interceptor.CommandInterceptor;

import java.util.List;

public class UserQueryImpl extends AbstractVariableQueryImpl<UserQuery, User> implements  UserQuery{

    public UserQueryImpl(){
    }

    public UserQueryImpl(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    @Override
    public long executeCount(CommandContext commandContext) {
        return commandContext.getUserEntityManager().findUserCountByQueryCriteria(this);
    }

    @Override
    public List<User> executeList(CommandContext commandContext, Page page) {
        List<User> users =  commandContext.getUserEntityManager().findUserCountByQueryCriteria()
        return ;
    }

    @Override
    public UserQuery userId(String userId) {
        return null;
    }

    @Override
    public UserQuery taskName(String name) {
        return null;
    }

    @Override
    public UserQuery taskNameIn(List<String> nameList) {
        return null;
    }

    @Override
    public CommandInterceptor getNext() {
        return null;
    }

    @Override
    public void setNext(CommandContext next) {

    }
}
