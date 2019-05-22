package com.github.hollykunge.security.admin.mapper;

import com.github.hollykunge.security.admin.entity.Element;
import com.github.hollykunge.security.admin.entity.Menu;
import com.github.hollykunge.security.admin.entity.Role;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends Mapper<Role> {
    List<Role> getAllSystemRole ();
    List<Menu> getSystemRoleMenu (@Param("roleId") String roleId);
    List<Element> getSystemRoleElement (Map<String,String> map);
}