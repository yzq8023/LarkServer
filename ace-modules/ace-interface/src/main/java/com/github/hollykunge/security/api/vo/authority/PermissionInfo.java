package com.github.hollykunge.security.api.vo.authority;

import java.io.Serializable;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author 协同设计小组
 * @create 2017-06-22 15:19
 */
public class PermissionInfo implements Serializable{
    private String roleId;

    private String permissionId;
    private String permissionName;
    private String actions;

    private String actionList;
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
