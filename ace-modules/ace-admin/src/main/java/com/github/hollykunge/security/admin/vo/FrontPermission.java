package com.github.hollykunge.security.admin.vo;

import java.util.List;

/**
 * 角色所属功能
 *
 * @author: holly
 * @since: 2019/5/9
 */
public class FrontPermission {
    //角色id
    private String roleId;
    //菜单权限id
    private String permissionId;
    //菜单权限名称
    private String permissionName;
    //功能集合
    private String actions;
    //暂时没用上null
    private String actionList;
    //暂时没用上null
    private String dataAccess;

    private List<ActionEntitySet> actionEntitySetList;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public String getActionList() {
        return actionList;
    }

    public void setActionList(String actionList) {
        this.actionList = actionList;
    }

    public String getDataAccess() {
        return dataAccess;
    }

    public void setDataAccess(String dataAccess) {
        this.dataAccess = dataAccess;
    }

    public List<ActionEntitySet> getActionEntitySetList() {
        return actionEntitySetList;
    }

    public void setActionEntitySetList(List<ActionEntitySet> actionEntitySetList) {
        this.actionEntitySetList = actionEntitySetList;
    }
}
