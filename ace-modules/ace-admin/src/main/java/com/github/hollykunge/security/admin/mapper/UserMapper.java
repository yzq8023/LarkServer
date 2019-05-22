package com.github.hollykunge.security.admin.mapper;

import com.github.hollykunge.security.admin.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
    List<User> selectUsersByOrgId(String orgId);

    /**
     *  通过角色ID查询用户列表
     * @param roleId 角色ID
     * @return 用户列表
     */
    List<User> getUsersByRoleId(@Param("roleId") String roleId);

    /**
     *  通过组织ID查询用户列表
     * @param orgId 组织ID
     * @return 用户列表
     */
    List<User> getUsersByOrgId(@Param("orgId") String orgId);
}