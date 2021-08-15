package com.example.demo.impl.cfg;

import com.example.demo.interceptor.Command;
import com.example.demo.interceptor.CommandConfig;
import com.example.demo.interceptor.CommandExecutor;
import com.example.demo.interceptor.CommandInterceptor;

public class CommandExecutorImpl implements CommandExecutor {

    protected CommandConfig defaultConfig;
    protected CommandInterceptor first;

    public CommandExecutorImpl(CommandConfig defaultConfig, CommandInterceptor first) {
        this.defaultConfig = defaultConfig;
        this.first = first;
    }

    public CommandInterceptor getFirst() {
        return first;
    }

    public void setFirst(CommandInterceptor commandInterceptor) {
        this.first = commandInterceptor;
    }

    @Override
    public CommandConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public <T> T execute(Command<T> command) {
        return execute(defaultConfig, command);
    }

    @Override
    public <T> T execute(CommandConfig config, Command<T> command) {
        return first.execute(config, command);
    }
}
