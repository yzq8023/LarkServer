package com.github.hollykunge.servicewebservice.dao;

import com.github.hollykunge.servicewebservice.model.Person;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
public interface personMapperDao {

    @Mapper
   public Person findByName();
}
