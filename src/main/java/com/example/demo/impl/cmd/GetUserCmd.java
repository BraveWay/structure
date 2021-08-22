package com.example.demo.impl.cmd;

import com.example.demo.core.CommandContext;
import com.example.demo.interceptor.Command;
import com.example.demo.persistence.entity.User;

import java.io.Serializable;

public class GetUserCmd implements Command<User>, Serializable {

    protected String userId;

    public GetUserCmd(String userId){
        this.userId = userId;
    }


    @Override
    public User execute(CommandContext commandContext) {
        return commandContext.getUserEntityManager().findById(userId);
    }
}
