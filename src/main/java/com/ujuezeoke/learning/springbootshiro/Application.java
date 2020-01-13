package com.ujuezeoke.learning.springbootshiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;

@EnableWebMvc
@SpringBootApplication
public class Application {

    @Autowired
    private SecurityManager appSecurityManager;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void initShiro(){
        SecurityUtils.setSecurityManager(appSecurityManager);
    }
}
