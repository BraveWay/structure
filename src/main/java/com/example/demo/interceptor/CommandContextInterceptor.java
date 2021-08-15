package com.example.demo.interceptor;

import com.example.demo.ConfigurationImpl;
import com.example.demo.core.CommandContext;
import com.example.demo.impl.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandContextInterceptor extends AbstractCommandInterceptor{

    private static final Logger log = LoggerFactory.getLogger(CommandContextInterceptor.class);

    protected CommandContextFactory commandContextFactory;
    protected ConfigurationImpl configurationImpl;

    public CommandContextInterceptor() {
    }

    public CommandContextInterceptor(CommandContextFactory commandContextFactory, ConfigurationImpl configurationImpl) {
        this.commandContextFactory = commandContextFactory;
        this.configurationImpl = configurationImpl;
    }

    public <T> T execute(CommandConfig config, Command<T> command) {
        CommandContext context = Context.getCommandContext();
        if(context == null){
            context = commandContextFactory.createCommandContext(command);
        }

        try {

            // Push on stack
            Context.setCommandContext(context);
            Context.setConfigurationImpl(configurationImpl);
            return next.execute(config, command);

        } catch (Throwable e) {
            e.printStackTrace();
//            context.exception(e);
        } finally {
            try {
                context.close();
            } finally {

                // Pop from stack
                Context.removeCommandContext();
                Context.removeConfigurationImpl();

            }
        }

        return null;
    }

    public CommandContextFactory getCommandContextFactory() {
        return commandContextFactory;
    }

    public void setCommandContextFactory(CommandContextFactory commandContextFactory) {
        this.commandContextFactory = commandContextFactory;
    }

    public ConfigurationImpl getConfigurationImpl() {
        return configurationImpl;
    }

    public void setProcessEngineContext(ConfigurationImpl configurationImpl) {
        this.configurationImpl = configurationImpl;
    }
}
