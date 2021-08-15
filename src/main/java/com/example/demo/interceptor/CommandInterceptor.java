package com.example.demo.interceptor;

public interface CommandInterceptor {

    <T> T execute(CommandConfig config, Command<T> command);

    CommandInterceptor getNext();

    void setNext(CommandInterceptor next);

}
