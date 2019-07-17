package com.github.hollykunge.servicewebservice.controller;

import com.github.hollykunge.servicewebservice.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import com.github.hollykunge.servicewebservice.service.personService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping(value = "/person")
@EnableAutoConfiguration
public class PersonController {
    @Autowired
   private personService personservice;
    @RequestMapping(value = "/show")
    public Person getUsers(){

        return personservice.queryPersonByName();
    }

}
