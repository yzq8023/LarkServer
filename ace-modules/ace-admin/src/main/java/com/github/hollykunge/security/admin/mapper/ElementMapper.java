package com.github.hollykunge.security.admin.mapper;

import com.github.hollykunge.security.admin.entity.Element;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ElementMapper extends Mapper<Element> {

    /**
     *  根据用户ID查询菜单列表
     * @param userId 用户ID
     * @return 菜单列表
     */
  List<Element> getMenusByUserId(@Param("userId") String userId);

    /**
     *  根据角色ID查询菜单列表
     * @param roleId 角色ID
     * @return 菜单列表
     */
  List<Element> getMenusByRoleId(@Param("roleId") String roleId);

    /**
     * 根据角色id,菜单id获取ResourceMap中的element
     * @param roleId  角色id
     * @param menuId 查询类型
     * @param type
     * @return
     */
    List<Element> getAuthorityMenuElement(@Param("roleId")String roleId,@Param("menuId") String menuId,
                             @Param("type")String type);

}