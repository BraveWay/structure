package com.example.demo.interceptor;

import com.example.demo.ConfigurationImpl;
import com.example.demo.core.CommandContext;

public class CommandContextFactory {

    protected ConfigurationImpl configurationImpl;

    public CommandContext createCommandContext(Command<?> cmd) {
        return new CommandContext(cmd, configurationImpl);
    }

    // getters and setters
    // //////////////////////////////////////////////////////

    public ConfigurationImpl getConfigurationImpl() {
        return configurationImpl;
    }

    public void setConfigurationImpl(ConfigurationImpl configurationImpl) {
        this.configurationImpl = configurationImpl;
    }
}
