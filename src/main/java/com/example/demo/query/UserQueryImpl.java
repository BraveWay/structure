package com.example.demo.query;

import com.example.demo.core.CommandContext;
import com.example.demo.core.query.AbstractVariableQueryImpl;
import com.example.demo.impl.Page;
import com.example.demo.persistence.entity.User;
import com.example.demo.interceptor.CommandExecutor;
import com.example.demo.interceptor.CommandInterceptor;

import java.util.List;

public class UserQueryImpl extends AbstractVariableQueryImpl<UserQuery, User> implements  UserQuery{

    protected String userId ;

    public UserQueryImpl(){
    }

    public UserQueryImpl(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }


    @Override
    public List<User> executeList(CommandContext commandContext, Page page) {
        return commandContext.getUserEntityManager().findUsersByQueryCriteria(this);
    }

    @Override
    public long executeCount(CommandContext commandContext) {
        return commandContext.getUserEntityManager().findUserCountByQueryCriteria(this);
    }



    @Override
    public UserQuery userId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public UserQuery taskName(String name) {
        return null;
    }

    @Override
    public UserQuery taskNameIn(List<String> nameList) {
        return null;
    }

}
