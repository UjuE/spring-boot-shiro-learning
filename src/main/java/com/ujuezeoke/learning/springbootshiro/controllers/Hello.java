package com.ujuezeoke.learning.springbootshiro.controllers;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/hello")
public class Hello {

    @GetMapping
    @RequiresUser
    public String hello(Principal principal){

        return "Hello, "+principal.getName();
    }
}
