package com.github.hollykunge.security.admin.mapper;

import com.github.hollykunge.security.admin.entity.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
    List<User> selectUsersByOrgId(String orgId);
}