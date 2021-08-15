package com.example.demo.impl;

import com.example.demo.ConfigurationImpl;
import com.example.demo.core.CommandContext;

import java.util.Stack;

public class Context {

    protected static ThreadLocal<Stack<CommandContext>> commandContextThreadLocal = new ThreadLocal<Stack<CommandContext>>();
    protected static ThreadLocal<Stack<ConfigurationImpl>> configurationImplThreadLocal = new ThreadLocal<Stack<ConfigurationImpl>>();


    public static CommandContext getCommandContext() {
        Stack<CommandContext> stack = getStack(commandContextThreadLocal);
        if (stack.isEmpty()) {
            return null;
        }
        return stack.peek();
    }

    public static void setCommandContext(CommandContext commandContext){
        getStack(commandContextThreadLocal).push(commandContext);
    }

    public static void removeCommandContext() {
        getStack(commandContextThreadLocal).pop();
    }

    public static ConfigurationImpl getConfigurationImpl() {
        Stack<ConfigurationImpl> stack = getStack(configurationImplThreadLocal);
        if (stack.isEmpty()) {
            return null;
        }
        return stack.peek();
    }

    public static void setConfigurationImpl(ConfigurationImpl configurationImpl){
        getStack(configurationImplThreadLocal).push(configurationImpl);
    }

    public static void removeConfigurationImpl() {
        getStack(configurationImplThreadLocal).pop();
    }

    protected static <T> Stack<T> getStack(ThreadLocal<Stack<T>> threadLocal) {
        Stack<T> stack = threadLocal.get();
        if (stack == null) {
            stack = new Stack<T>();
            threadLocal.set(stack);
        }
        return stack;
    }
}
