package com.example.demo.interceptor;

import com.example.demo.core.CommandContext;
import com.example.demo.impl.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandInvoker extends AbstractCommandInterceptor{

    private static final Logger logger = LoggerFactory.getLogger(CommandInvoker.class);

    @Override
    public <T> T execute(CommandConfig config, Command<T> command) {
        final CommandContext commandContext = Context.getCommandContext();
        commandContext.setResult(command.execute(commandContext));
        return  (T) commandContext.getResult();
    }
}
