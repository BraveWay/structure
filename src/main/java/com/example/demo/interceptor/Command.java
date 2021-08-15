package com.example.demo.interceptor;

import com.example.demo.core.CommandContext;

public interface Command<T> {

    T execute(CommandContext commandContext);

    CommandInterceptor getNext();

    void setNext(CommandContext next);

}
