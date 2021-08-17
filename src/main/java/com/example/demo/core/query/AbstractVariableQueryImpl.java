package com.example.demo.core.query;

import com.example.demo.core.CommandContext;
import com.example.demo.interceptor.CommandExecutor;

public abstract class AbstractVariableQueryImpl<T extends Query<?,?> , U> extends AbstractQuery<T,U> {

    private static final long serialVersionUID = 1L;

    public AbstractVariableQueryImpl(){
    }

    public AbstractVariableQueryImpl(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    public abstract long executeCount(CommandContext commandContext);

}
