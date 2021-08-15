package com.example.demo.config;

import com.example.demo.ConfigurationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class InitMybatisConfig {


    @Bean
    public ConfigurationImpl initConfigurationImpl(DataSource dataSource){
        ConfigurationImpl impl = new ConfigurationImpl();
        impl.setDataSource(dataSource);
        impl.init();
        return impl;
    }
}
