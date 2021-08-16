package com.example.demo.query;

import com.example.demo.core.CommandContext;
import com.example.demo.entity.User;
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
    public UserQuery desc() {
        return null;
    }

    @Override
    public User singleResult() {
        return null;
    }

    @Override
    public List<User> list() {
        return null;
    }

    @Override
    public List<User> listPage(int firstResult, int maxResults) {
        return null;
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