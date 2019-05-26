package com.github.hollykunge.security.admin.mapper;

import com.github.hollykunge.security.admin.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {

    /**
     * 通过组织ID查询用户列表，关联表中查询
     *
     * @param orgId 组织ID
     * @return 用户列表
     */
    List<User> selectUsersByOrgId(@Param("orgId") String orgId);

    /**
     * 通过角色ID查询用户列表，关联表中查询
     *
     * @param roleId 角色ID
     * @return 用户列表
     */
    List<User> selectUsersByRoleId(@Param("roleId") String roleId);

    /**
     * 根据角色id批量删除用户
     * @param roleId 角色ID
     * @return 数量
     */
    int deleteUsersByRoleId (String roleId);
}