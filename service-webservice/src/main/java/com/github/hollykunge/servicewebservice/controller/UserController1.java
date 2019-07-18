package com.github.hollykunge.servicewebservice.controller;


import com.github.hollykunge.servicewebservice.dao.UserDAO;
import com.github.hollykunge.servicewebservice.model.Userr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/try")
@EnableAutoConfiguration
public class UserController1 {
    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/user")
    public List<Userr> getUsers(){
        System.out.println("guxiaoqiuguxiaoiqu");
        return userDAO.selectUsers();
    }
    @RequestMapping(value = "/hello")
    public String display(){

        return "hello world mapper is difficult";
    }

}
