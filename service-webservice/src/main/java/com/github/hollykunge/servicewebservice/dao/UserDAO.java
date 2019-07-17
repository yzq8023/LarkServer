package com.github.hollykunge.servicewebservice.dao;

import com.github.hollykunge.servicewebservice.model.Userr;

import java.util.List;

  public interface UserDAO {
        List<Userr> selectUsers();
    }
