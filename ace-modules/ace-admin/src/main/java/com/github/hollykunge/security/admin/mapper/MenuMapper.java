package com.github.hollykunge.security.admin.mapper;

import com.github.hollykunge.security.admin.entity.Menu;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MenuMapper extends Mapper<Menu> {
    List<Menu> selectMenuByRoleId(int roleId, String resourceType);
}