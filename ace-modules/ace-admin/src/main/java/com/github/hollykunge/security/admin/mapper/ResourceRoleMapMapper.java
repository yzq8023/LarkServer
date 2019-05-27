package com.github.hollykunge.security.admin.mapper;

import com.github.hollykunge.security.admin.entity.ResourceRoleMap;
import com.github.hollykunge.security.admin.vo.ActionEntitySet;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * <p>角色资源类型mapper接口</p>
 * @author zhhongyu
 */
public interface ResourceRoleMapMapper extends Mapper<ResourceRoleMap> {
    /**
     * 根据角色id和资源类型删除资源列表
     * @param roleId 角色id
     * @param type 资源类型
     * @return
     */
    int deleteByAuthorityIdAndResourceType(String roleId, String type);

    /**
     * 根据角色获取ResourceMap中的element
     * @param roleId 角色id
     * @param type 查询类型
     * @return
     */
    List<ActionEntitySet> getElement(String roleId,String type);

}