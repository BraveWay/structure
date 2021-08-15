package com.example.demo;

import com.example.demo.common.MybatisUserDataManager;
import com.example.demo.interceptor.CommandContextFactory;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private ConfigurationImpl config;

    @Test
    void contextLoads() {

        try{
            UserService userService = config.getUserService();
            System.out.println(userService.createUserQuery().count());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
