package com.example.demo.service.impl;

import com.example.demo.ConfigurationImpl;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.interceptor.CommandExecutor;
import com.example.demo.query.UserQuery;
import com.example.demo.query.UserQueryImpl;
import com.example.demo.service.ServiceImpl;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl extends ServiceImpl implements UserService {

    public UserServiceImpl(){

    }

    public UserServiceImpl(ConfigurationImpl configurationImpl){
        super(configurationImpl);
    }

    public UserServiceImpl(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

  public UserQuery createUserQuery(){
        return new UserQueryImpl(commandExecutor);
  }

}
