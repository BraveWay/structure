package com.example.demo.interceptor;

public interface CommandExecutor {

    /**
     * @return the default {@link CommandConfig}, used if none is provided.
     */
    CommandConfig getDefaultConfig();

    /**
     * Execute a command with the specified {@link CommandConfig}.
     */
    <T> T execute(CommandConfig config, Command<T> command);

    /**
     * Execute a command with the default {@link CommandConfig}.
     */
    <T> T execute(Command<T> command);
}
