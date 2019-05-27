package com.github.hollykunge.security.admin.vo;

import lombok.Data;

import java.util.List;

/**
 * 角色所属功能
 *
 * @author: holly
 * @since: 2019/5/9
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
     * 暂时没用上null
     */
    private String dataAccess;

    private List<ActionEntitySet> actionEntitySetList;

}
