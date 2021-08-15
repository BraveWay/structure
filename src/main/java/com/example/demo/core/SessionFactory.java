package com.example.demo.core;

import com.example.demo.core.CommandContext;
import com.example.demo.core.Session;

public interface SessionFactory {

    Class<?> getSessionType();
    Session openSession(CommandContext commandContext);
}
