package com.example.demo.service;

import com.example.demo.ConfigurationImpl;
import com.example.demo.interceptor.CommandExecutor;

public class ServiceImpl {

    protected ConfigurationImpl configurationImpl;

    protected CommandExecutor commandExecutor;

    public ServiceImpl() {

    }

    public ServiceImpl(ConfigurationImpl configurationImpl) {
        this.configurationImpl = configurationImpl;
    }

    public ServiceImpl(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }


    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

    public void setCommandExecutor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }
}
