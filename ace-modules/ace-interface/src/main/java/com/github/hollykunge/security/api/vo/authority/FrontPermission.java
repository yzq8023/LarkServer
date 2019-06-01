package com.github.hollykunge.security.api.vo.authority;

import lombok.Data;

import java.util.List;

/**
 * @description: 权限业务实体
 * @author: dd
 * @since: 2019-06-01
 */
@Data
public class FrontPermission {
    /**
     * 角色id
     */
    private String roleId;
    /**
     * permissionId->menuId
     */
    private String menuId;
    /**
     * permissionName->title
     */
    private String title;
    /**
     * actions->methods
     */
    private String methods;
    /**
     * 暂时没用上null
     */
    private String actionList;
    /**
     * 权限资源路径
     */
    private String uri;

    private List<ActionEntitySet> actionEntitySetList;

}
