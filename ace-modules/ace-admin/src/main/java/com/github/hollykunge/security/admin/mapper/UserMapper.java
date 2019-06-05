package com.github.hollykunge.security.admin.mapper;

import com.github.hollykunge.security.admin.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {

    /**
     * 通过组织ID查询用户列表，关联表中查询
     *
     * @param orgCode 组织编码
     * @return 用户列表
     */
    List<User> selectUsersByOrgCode(@Param("orgCode") String orgCode);

    /**
     * 通过角色ID查询用户列表，关联表中查询
     *
     * @param roleId 角色ID
     * @return 用户列表
     */
    List<User> selectUsersByRoleId(@Param("roleId") String roleId);
}